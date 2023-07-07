package com.example.inventoryservice.grpcService;

import com.example.inventoryService.*;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.service.InventoryService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {


    InventoryService inventoryService;


    public InventoryServiceImpl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @Override
    public void createInventory(CreateInventoryRequest request, StreamObserver<CreateInventoryResponse> responseObserver) {
        try {
            Inventory inventoryReq = InventoryMapper.makeInventoryFromInventoryDTO(request.getInventory());
            Inventory inventoryRes = inventoryService.createInventory(inventoryReq);
            InventoryDTO inventoryDTO = InventoryMapper.makeInventoryDTOfromInventory(inventoryRes);
            CreateInventoryResponse createInventoryResponse = CreateInventoryResponse
                    .newBuilder().setInventory(inventoryDTO).build();
            responseObserver.onNext(createInventoryResponse);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }

    }

    @Override
    public void getInventory(GetInventoryRequest request, StreamObserver<GetInventoryResponse> responseObserver) {
        try {
            Inventory inventory = inventoryService.getInventoryById(request.getId()).get();
            InventoryDTO responseDto = InventoryMapper.makeInventoryDTOfromInventory(inventory);
            GetInventoryResponse getInventoryResponse = GetInventoryResponse.
                    newBuilder().setInventory(responseDto).build();
            responseObserver.onNext(getInventoryResponse);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void updateInventory(UpdateInventoryRequest request, StreamObserver<UpdateInventoryResponse> responseObserver) {
        try {
            Long inventoryId = request.getId();
            InventoryDTO updateReqDto = request.getInventory();
            Inventory updateInventory = InventoryMapper.makeInventoryFromInventoryDTO(updateReqDto);
            Inventory updatedInventoryResponse = inventoryService.updateInventory(inventoryId, updateInventory);
            InventoryDTO updateRes = InventoryMapper.makeInventoryDTOfromInventory(updatedInventoryResponse);
            UpdateInventoryResponse updateInventoryResponse = UpdateInventoryResponse
                    .newBuilder().setInventory(updateRes).build();
            responseObserver.onNext(updateInventoryResponse);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteInventory(DeleteInventoryRequest request, StreamObserver<DeleteInventoryResponse> responseObserver) {
        super.deleteInventory(request, responseObserver);
    }

    @Override
    public void getInventoryWithFilter(Filters request, StreamObserver<InventoryDTO> responseObserver) {
        try {
            inventoryService.getAllInventories().stream()
                    .map(InventoryMapper::makeInventoryDTOfromInventory)
                    .filter(inventoryDTO -> {
                        if (request.getInventoryState() != null) {
                            if (request.getDiscountedState() != null) {
                                if (inventoryDTO.getInventoryState() == request.getInventoryState() &&
                                        inventoryDTO.getDiscountedState() == request.getDiscountedState()) {
                                    return true;
                                }
                            } else {
                                return inventoryDTO.getInventoryState() == request.getInventoryState();
                            }
                        } else {
                            if (request.getDiscountedState() != null) {
                                return inventoryDTO.getDiscountedState() == request.getDiscountedState();
                            }
                        }
                        return true;
                    })
                    .forEach(responseObserver::onNext);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }
}
