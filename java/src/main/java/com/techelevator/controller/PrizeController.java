package com.techelevator.controller;


import com.techelevator.dao.JdbcPrizeDao;
import com.techelevator.dao.PrizeDao;
import com.techelevator.model.Prize;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class PrizeController {
    private final PrizeDao prizeDao;

    public PrizeController(PrizeDao prizeDao) {
        this.prizeDao = prizeDao;
    }

    @GetMapping("/prize")
    public List<Prize> listPrizesByFamily(Principal principal) {
        return prizeDao.listPrizesByFamily(principal.getName());
    }

    @GetMapping("/prize/{prizeId}")
    public Prize getPrizeById(Principal principal, @PathVariable Integer prizeId) throws Exception {
        return prizeDao.getPrizeById(principal.getName(), prizeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/prize")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public Prize createPrize(Principal principal, @Valid @RequestBody Prize prize) throws Exception {
        return prizeDao.createPrize(principal.getName(), prize);
    }

    @PutMapping("/prize/{prizeId}")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public Prize updatePrize(Principal principal, @PathVariable Integer prizeId, @Valid @RequestBody Prize prize) throws Exception {
        return prizeDao.updatePrize(principal.getName(), prizeId, prize);
    }

    @DeleteMapping("/prize/{prizeId}")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public boolean deletePrize(Principal principal, @PathVariable Integer prizeId) throws Exception {
        return prizeDao.deletePrize(principal.getName(), prizeId);
    }







}
