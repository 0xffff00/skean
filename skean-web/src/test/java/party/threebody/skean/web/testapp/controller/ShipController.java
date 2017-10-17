package party.threebody.skean.web.testapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.web.testapp.domain.Ship;
import party.threebody.skean.web.testapp.mvc.controller.CrudFunctionsBuilder;
import party.threebody.skean.web.testapp.mvc.controller.SinglePKCrudRestController;
import party.threebody.skean.web.testapp.service.ShipService;

@RestController
@RequestMapping("ships")
public class ShipController extends SinglePKCrudRestController<Ship, String> {

    @Autowired
    ShipService shipService;

    @Override
    public void buildCrudFunctions(CrudFunctionsBuilder<Ship, String> builder) {
        builder.countReader(shipService::countShips)
                .listReader(shipService::listShips)
                .oneReader(shipService::getShip)
                .creator(shipService::createAndGet)
                .entireUpdater(shipService::update)
                .partialUpdater(shipService::partialUpdate)
                .deleter(shipService::delete)
                .pkGetter(Ship::getCode);
    }

}
