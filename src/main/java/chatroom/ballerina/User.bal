import ballerina/http;
import ballerina/log;

// By default, Ballerina exposes an HTTP service via HTTP/1.1.
service user on new http:Listener(9090) {
    int room = 1;
    // Resource functions are invoked with the HTTP caller and the incoming request as arguments.
    resource function sayHello(http:Caller caller, http:Request req) {
        // Send a response back to the caller.
        var result = caller->respond("Hello, World!"+room);
        room = room + 1;
        if (result is error) {
            log:printError("Error sending response", err = result);
        }
    }
}
