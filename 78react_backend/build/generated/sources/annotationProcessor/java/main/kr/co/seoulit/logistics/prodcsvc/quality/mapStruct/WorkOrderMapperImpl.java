package kr.co.seoulit.logistics.prodcsvc.quality.mapStruct;

import javax.annotation.processing.Generated;
import kr.co.seoulit.logistics.prodcsvc.quality.Entity.WorkOrderEntity;
import kr.co.seoulit.logistics.prodcsvc.quality.to.WorkOrderInfoTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-08T08:45:39+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 17.0.10 (Amazon.com Inc.)"
)
public class WorkOrderMapperImpl implements WorkOrderMapper {

    @Override
    public WorkOrderInfoTO entitiyToDTO(WorkOrderEntity workOrderEntity) {
        if ( workOrderEntity == null ) {
            return null;
        }

        WorkOrderInfoTO workOrderInfoTO = new WorkOrderInfoTO();

        workOrderInfoTO.setWorkOrderNo( workOrderEntity.getWorkOrderNo() );
        workOrderInfoTO.setMrpGatheringNo( workOrderEntity.getMrpGatheringNo() );
        workOrderInfoTO.setMrpNo( workOrderEntity.getMrpNo() );
        workOrderInfoTO.setItemClassification( workOrderEntity.getItemClassification() );
        workOrderInfoTO.setItemCode( workOrderEntity.getItemCode() );
        workOrderInfoTO.setItemName( workOrderEntity.getItemName() );
        workOrderInfoTO.setUnitOfMrp( workOrderEntity.getUnitOfMrp() );
        workOrderInfoTO.setRequiredAmount( workOrderEntity.getRequiredAmount() );
        workOrderInfoTO.setWorkSiteCode( workOrderEntity.getWorkSiteCode() );
        workOrderInfoTO.setWorkSiteName( workOrderEntity.getWorkSiteName() );
        workOrderInfoTO.setProductionProcessCode( workOrderEntity.getProductionProcessCode() );
        workOrderInfoTO.setProductionProcessName( workOrderEntity.getProductionProcessName() );
        workOrderInfoTO.setInspectionStatus( workOrderEntity.getInspectionStatus() );
        workOrderInfoTO.setProductionStatus( workOrderEntity.getProductionStatus() );
        workOrderInfoTO.setCompletionStatus( workOrderEntity.getCompletionStatus() );

        return workOrderInfoTO;
    }
}
