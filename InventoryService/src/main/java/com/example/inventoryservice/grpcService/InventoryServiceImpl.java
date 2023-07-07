package com.example.inventoryservice.grpcService;

import com.example.inventoryService.*;
import io.grpc.stub.StreamObserver;

public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
    @Override
    public void createInventory(CreateInventoryRequest request, StreamObserver<CreateInventoryResponse> responseObserver) {
        super.createInventory(request, responseObserver);
    }

    @Override
    public void getInventory(GetInventoryRequest request, StreamObserver<GetInventoryResponse> responseObserver) {
        super.getInventory(request, responseObserver);
    }

    @Override
    public void updateInventory(UpdateInventoryRequest request, StreamObserver<UpdateInventoryResponse> responseObserver) {
        super.updateInventory(request, responseObserver);
    }

    @Override
    public void deleteInventory(DeleteInventoryRequest request, StreamObserver<DeleteInventoryResponse> responseObserver) {
        super.deleteInventory(request, responseObserver);
    }

    @Override
    public void getInventoryWithFilter(Filters request, StreamObserver<InventoryDTO> responseObserver) {
        super.getInventoryWithFilter(request, responseObserver);
    }
}
