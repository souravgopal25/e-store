package com.example.inventoryservice.grpcService;


import com.example.dealService.*;
import com.example.inventoryservice.entity.Deal;
import com.example.inventoryservice.exception.DealNotFoundException;
import com.example.inventoryservice.service.DealService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@GrpcService
public class DealServiceImpl extends DealServiceGrpc.DealServiceImplBase {
    DealService dealService;

    public DealServiceImpl(DealService dealService) {
        this.dealService = dealService;
    }

    private Deal makeDealFromDealDTO(DealDTO dealDTO) {
        return Deal.builder().name(dealDTO.getName())
                .inventoryId(dealDTO.getInventoryId())
                .startRaisingDate(getLocalDateTimeFromTimeStamp(dealDTO.getStartRaisingDate()))
                .closeRaisingDate(getLocalDateTimeFromTimeStamp(dealDTO.getCloseRaisingDate()))
                .build();
    }

    private DealDTO makeDealDTOFromDeal(Deal deal) {
        return DealDTO.newBuilder().setId(deal.getId())
                .setName(deal.getName())
                .setInventoryId(deal.getInventoryId())
                .setStartRaisingDate(getTimeStampFromLocalDateTime(deal.getStartRaisingDate()))
                .setCloseRaisingDate(getTimeStampFromLocalDateTime(deal.getCloseRaisingDate()))
                .setCreatedAt(getTimeStampFromLocalDateTime(deal.getCreatedAt()))
                .setUpdatedAt(getTimeStampFromLocalDateTime(deal.getUpdatedAt()))
                .build();
    }

    private LocalDateTime getLocalDateTimeFromTimeStamp(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

    private Timestamp getTimeStampFromLocalDateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
        return timestamp;
    }

    @Override
    public void createDeal(CreateDealRequest request, StreamObserver<CreateDealResponse> responseObserver) {
        try {
            Deal deal = makeDealFromDealDTO(request.getDeal());
            Deal createdDeal = dealService.createDeal(deal);
            DealDTO responseDTO = makeDealDTOFromDeal(createdDeal);
            responseObserver.onNext(CreateDealResponse.newBuilder().setDeal(responseDTO).build());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }

    }

    @Override
    public void getDeal(GetDealRequest request, StreamObserver<GetDealResponse> responseObserver) {
        try {
            Long dealId = request.getId();
            Optional<Deal> optionalDeal = dealService.getDealById(dealId);
            if (optionalDeal.isEmpty()) {
                throw new DealNotFoundException(dealId);
            }
            DealDTO dealDTO = makeDealDTOFromDeal(optionalDeal.get());
            GetDealResponse response = GetDealResponse.newBuilder().setDeal(dealDTO).build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void updateDeal(UpdateDealRequest request, StreamObserver<UpdateDealResponse> responseObserver) {
        try {
            Long dealId = request.getId();
            Deal updateReq = makeDealFromDealDTO(request.getDeal());
            Deal updatedDeal = dealService.updateDeal(dealId, updateReq);
            DealDTO updatedDealDto = makeDealDTOFromDeal(updatedDeal);
            UpdateDealResponse updateDealResponse = UpdateDealResponse.newBuilder().setDeal(updatedDealDto).build();
            responseObserver.onNext(updateDealResponse);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteDeal(DeleteDealRequest request, StreamObserver<DeleteDealResponse> responseObserver) {
        try {
            Long dealId = request.getId();
            dealService.deleteDeal(dealId);
            DeleteDealResponse deleteDealResponse = DeleteDealResponse.newBuilder()
                    .setMessage("Deal Deleted Success").build();
            responseObserver.onNext(deleteDealResponse);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getAllDeals(EmptyRequest request, StreamObserver<DealDTO> responseObserver) {
        try {
            dealService.getAllDeals().stream()
                    .map(this::makeDealDTOFromDeal)
                    .forEach(responseObserver::onNext);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        } finally {
            responseObserver.onCompleted();
        }
    }

}
