package com.procurement.budget.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.QueryBuilder.*
import com.procurement.budget.model.dto.bpe.ResponseDto
import com.procurement.budget.model.entity.HistoryEntity
import com.procurement.budget.utils.localNowUTC
import com.procurement.budget.utils.toDate
import com.procurement.budget.utils.toJson
import org.springframework.stereotype.Service

@Service
class HistoryDao(private val session: Session) {

    fun getHistory(operationId: String, command: String): HistoryEntity? {
        val query = select()
                .all()
                .from(HISTORY_TABLE)
                .where(eq(OPERATION_ID, operationId))
                .and(eq(COMMAND, command))
                .limit(1)
        val row = session.execute(query).one()
        return if (row != null) HistoryEntity(
                row.getString(OPERATION_ID),
                row.getString(COMMAND),
                row.getTimestamp(OPERATION_DATE),
                row.getString(JSON_DATA)) else null
    }

    fun saveHistory(operationId: String, command: String, response: ResponseDto): HistoryEntity {
        val entity = HistoryEntity(
                operationId = operationId,
                command = command,
                operationDate = localNowUTC().toDate(),
                jsonData = toJson(response))

        val insert = insertInto(HISTORY_TABLE)
                .value(OPERATION_ID, entity.operationId)
                .value(COMMAND, entity.command)
                .value(OPERATION_DATE, entity.operationDate)
                .value(JSON_DATA, entity.jsonData)
        session.execute(insert)
        return entity
    }

    companion object {
        private const val HISTORY_TABLE = "budget_history"
        private const val OPERATION_ID = "operation_id"
        private const val COMMAND = "command"
        private const val OPERATION_DATE = "operation_date"
        private const val JSON_DATA = "json_data"
    }

}
