package com.procurement.budget.service;

import com.datastax.driver.core.utils.UUIDs;
import com.procurement.budget.dao.FsDao;
import com.procurement.budget.exception.ErrorException;
import com.procurement.budget.model.dto.bpe.ResponseDto;
import com.procurement.budget.model.dto.ei.EiDto;
import com.procurement.budget.model.dto.fs.FsDto;
import com.procurement.budget.model.dto.fs.FsRequestDto;
import com.procurement.budget.model.dto.fs.FsTenderDto;
import com.procurement.budget.model.dto.ocds.BudgetBreakdown;
import com.procurement.budget.model.dto.ocds.OrganizationReference;
import com.procurement.budget.model.dto.ocds.TenderStatus;
import com.procurement.budget.model.entity.FsEntity;
import com.procurement.budget.utils.DateUtil;
import com.procurement.budget.utils.JsonUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class FsServiceImpl implements FsService {

    private static final String SEPARATOR = "-";
    private static final String DATA_NOT_FOUND_ERROR = "FS not found.";
    private static final String INVALID_OWNER_ERROR = "FS invalid owner.";
    private final JsonUtil jsonUtil;
    private final DateUtil dateUtil;
    private final FsDao fsDao;
    private final EiService eiService;

    public FsServiceImpl(final JsonUtil jsonUtil,
                         final DateUtil dateUtil,
                         final FsDao fsDao,
                         final EiService eiService) {
        this.jsonUtil = jsonUtil;
        this.dateUtil = dateUtil;
        this.fsDao = fsDao;
        this.eiService = eiService;
    }

    @Override
    public ResponseDto createFs(final String cpId,
                                final String owner,
                                final FsRequestDto fsDto) {
        final FsDto fs = new FsDto();
        fs.setOcId(getOcId(cpId));
        fs.setDate(dateUtil.getNowUTC());
        /*payer*/
        fs.setPayer(fsDto.getTender().getProcuringEntity());
        processOrganizationReference(fs.getPayer());
        fsDto.getTender().setProcuringEntity(null);
        /*funder*/
        OrganizationReference buyer = fsDto.getBuyer();
        if (Objects.nonNull(buyer)) {
            processOrganizationReference(buyer);
            fs.setFunder(buyer);
        }
        /*source parties*/
        if (Objects.isNull(buyer)) {
            fs.getPlanning().getBudget().setVerified(true);
            EiDto eiDto = eiService.getEi(cpId);
            buyer = eiDto.getBuyer();
        }
        processSourceParties(fs.getPlanning().getBudget().getBudgetBreakdown(), buyer);
        /*tender*/
        fs.setTender(new FsTenderDto(cpId, TenderStatus.PLANNING, null));
        /*planning*/
        fs.setPlanning(fsDto.getPlanning());
        final FsEntity entity = getEntity(cpId, owner, fs);
        fsDao.save(entity);
        fs.setToken(entity.getToken().toString());
        return new ResponseDto<>(true, null, fs);
    }

    @Override
    public ResponseDto updateFs(final String cpId,
                                final String ocId,
                                final String token,
                                final String owner,
                                final FsDto fsDto) {
        final FsEntity entity = Optional.ofNullable(fsDao.getByCpIdAndOcIdAndToken(cpId, ocId, UUID.fromString(token)))
                .orElseThrow(() -> new ErrorException(DATA_NOT_FOUND_ERROR));
        if (!entity.getOwner().equals(owner)) throw new ErrorException(INVALID_OWNER_ERROR);
        final FsDto fs = jsonUtil.toObject(FsDto.class, entity.getJsonData());
        fs.setDate(dateUtil.getNowUTC());
        fs.setTender(fsDto.getTender());
        fs.setPlanning(fsDto.getPlanning());
        entity.setJsonData(jsonUtil.toJson(fs));
        fsDao.save(entity);
        return new ResponseDto<>(true, null, fs);
    }

    private void processSourceParties(final List<BudgetBreakdown> budgetBreakdowns, final OrganizationReference buyer) {
        budgetBreakdowns.forEach(b -> {
            final OrganizationReference sp = buyer;
            sp.setIdentifier(null);
            sp.setAdditionalIdentifiers(null);
            sp.setAddress(null);
            sp.setContactPoint(null);
            sp.setDetails(null);
            b.setSourceParty(sp);
        });
    }

    private void processOrganizationReference(final OrganizationReference or) {
        or.setId(or.getIdentifier().getScheme() + SEPARATOR + or.getIdentifier().getId());
    }


    private String getOcId(final String cpId) {
        return cpId + "-fs-" + dateUtil.getMilliNowUTC();
    }

    private Double getAmount(final FsDto fs) {
        return fs.getPlanning().getBudget().getAmount().getAmount();
    }

    private FsEntity getEntity(final String cpId, final String owner, final FsDto fs) {
        final FsEntity fsEntity = new FsEntity();
        fsEntity.setCpId(cpId);
        fsEntity.setOcId(fs.getOcId());
        fsEntity.setToken(UUIDs.random());
        fsEntity.setOwner(owner);
        fsEntity.setJsonData(jsonUtil.toJson(fs));
        fsEntity.setAmount(getAmount(fs));
        return fsEntity;
    }
}
