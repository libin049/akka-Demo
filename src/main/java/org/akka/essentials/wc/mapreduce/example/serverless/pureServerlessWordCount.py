import boto3
import re
def map(event):
    #s3_input is s3 url of txt file
    s3_input = event['s3_input']
    s3_output = event['s3_output']
    counter = {}
    # get input from S3/redis
    s3m = re.match(r's3://(.*)/(.*)', s3_input)
    bucket, key = s3m.group(1, 2)
    client = boto3.client('s3')
    data_stream = client.get_object(Key=key, Bucket=bucket)['Body']
    data = data_stream.read()
    # word count compute for data
    for line in data.splitlines():
        for word in line.split():
            if word not in counter:
                counter[word] = 1
            else:
                counter[word] += 1
    s3m = re.match(r's3://(.*)/(.*)', s3_input)
    bucket, key = s3m.group(1, 2)
    client = boto3.client('s3')
    #Serialization and save to S3/redis
    response = client.put_object(Key=key, Bucket=bucket, Body=serialization(counter),
                                 ACL="bucket-owner-full-control")

def reduce(event):
    #s3_input is the list of urls of serialization results of maps
    s3_inputs = event['s3_input']
    s3_output = event['s3_output']
    #get input from S3
    final_result = {}
    for s3_input in s3_inputs:
        s3m = re.match(r's3://(.*)/(.*)', s3_input)
        bucket, key = s3m.group(1, 2)
        client = boto3.client('s3')
        data_stream = client.get_object(Key=key, Bucket=bucket)['Body']
        data = data_stream.read()
        count = unserialization (data)
        for word in count:
            if word not in final_result:
                final_result[word] = count[word]
            else:
                final_result[word] += count[word]

    # Serialization and save to S3/redis
    response = client.put_object(Key=key, Bucket=bucket, Body=serialization(final_result),
                                ACL="bucket-owner-full-control")

def scheduling(event):
    client = boto3.client('lambda')
    client.invoke(
        FunctionName='map',
        InvocationType='RequestResponse', # invoke a function synchronously
        Payload='{s3_input:s3://ctf-pywren-188/1,s3_ouput:s3://ctf-pywren-188/1_out}')
    client.invoke(
        FunctionName='map',
        InvocationType='RequestResponse',  # invoke a function synchronously
        Payload='{s3_input:s3://ctf-pywren-188/2,s3_ouput:s3://ctf-pywren-188/2_out}')
    client.invoke(
        FunctionName='reduce',
        InvocationType='RequestResponse',  # invoke a function synchronously
        Payload='{s3_input:[s3://ctf-pywren-188/1_out,s3://ctf-pywren-188/2_out], \
                  s3_ouput:s3://ctf-pywren-188/out}')
