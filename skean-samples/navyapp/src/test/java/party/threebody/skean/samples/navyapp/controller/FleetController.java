package party.threebody.skean.samples.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.samples.navyapp.dao.FleetDAO;
import party.threebody.skean.samples.navyapp.domain.Fleet;
import party.threebody.skean.web.mvc.controller.MultiPKsCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.MultiPKsMatrixVarCrudRestController;

@RestController
@RequestMapping("fleets")
public class FleetController extends MultiPKsMatrixVarCrudRestController<Fleet> {

    @Autowired FleetDAO fleetDAO;

    @Override
    public void buildCrudFunctions(MultiPKsCrudFunctionsBuilder<Fleet> builder) {
        builder.fromMultiPKsCrudDAO(fleetDAO)
                .batchDeleteEnabled(true);
    }
}
