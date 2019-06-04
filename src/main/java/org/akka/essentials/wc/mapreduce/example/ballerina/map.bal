import ballerina/http;
import ballerina/log;
import ballerina/io;

service HelloWorldMessageListener = service {

    resource function onMessage(string message) {
        io:println("Response received from server: " + message);
    }

    resource function onError(error err) {
        io:println("Error reported from server: " + err.reason() + " - "
                + <string>err.detail().message);
    }

    resource function onComplete() {
        io:println("Server Complete Sending Response.");
    }
};


http:Client reduceEp = new("http://localhost:9091/Reduce");

// By default, Ballerina exposes an HTTP service via HTTP/1.1.
service Count on new http:Listener(9090) {
    @http:ResourceConfig {
            methods: ["POST"],
            path: "/"
        }
    resource function count(http:Caller caller, http:Request req) returns error? {
        // Send a response back to the caller.
        string s3_url = check req.getTextPayload();
        //read context from s3
        //compute word count
        map<int> countRes = {};
        countRes["hello"] = 1;
        countRes["world"] = 2;
        json out_put = "url";
        http:Response|error result = reduceEp->post("/reduce", out_put);
     // Create object to carry data back to caller
        http:Response response = new;

        // Objects and records can have function calls
        response.setTextPayload("Hello Ballerina!");
        //while(true){}
        // Send a response back to caller
        // Errors are ignored with '_'
        // -> indicates a synchronous network-bound call
      07  check caller->respond(response);
    }
}
