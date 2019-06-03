import ballerina/grpc;
import ballerina/log;
import ballerina/io;
import ballerina/http;

map<string> finalRes = {};
int count = 0;
service Reduce on new http:Listener(9091) {
    resource function reduce(http:Caller caller, http:Request req) {
        count = count + 1;
        io:println(count);
        var result = caller->respond(count);
        return;
    }
}