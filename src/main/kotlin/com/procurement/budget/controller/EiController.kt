package com.procurement.budget.controller

import com.procurement.budget.model.bpe.ResponseDto
import com.procurement.budget.model.dto.ei.request.EiCreate
import com.procurement.budget.model.dto.ei.request.EiUpdate
import com.procurement.budget.service.EiService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/ei")
class EiController(private val eiService: EiService) {

    @PostMapping
    fun createEi(@RequestParam("owner") owner: String,
                 @RequestParam("country") country: String,
                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                 @RequestParam("date") dateTime: LocalDateTime,
                 @Valid @RequestBody data: EiCreate): ResponseEntity<ResponseDto> {
        return ResponseEntity(eiService.createEi(
                owner = owner,
                country = country,
                dateTime = dateTime,
                eiDto = data), HttpStatus.CREATED)
    }

    @PutMapping
    fun updateEi(@RequestParam("cpid") cpId: String,
                 @RequestParam("owner") owner: String,
                 @RequestParam("token") token: String,
                 @Valid @RequestBody data: EiUpdate): ResponseEntity<ResponseDto> {
        return ResponseEntity(eiService.updateEi(
                owner = owner,
                cpId = cpId,
                token = token,
                eiDto = data), HttpStatus.OK)
    }
}
