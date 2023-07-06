package com.example.userservice.grpcService;


import com.example.helloService.HelloRequest;
import com.example.helloService.HelloResponse;
import com.example.helloService.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String message =request.getFirstName()+" "+request.getLastName()+" "+" Hey There!";
        responseObserver.onNext(HelloResponse.newBuilder().setGreeting(message).build());
        responseObserver.onCompleted();
    }
}
