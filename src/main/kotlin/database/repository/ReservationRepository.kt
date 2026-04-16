package database.repository

import database.Database
import model.payment.PayType
import model.seat.Seat
import model.seat.SeatColumn
import model.seat.SeatRow

class ReservationRepository {
    fun save(
        screeningId: Int,
        seat: Seat,
        totalPrice: Int,
        usedPoint: Int,
        payType: PayType,
    ) {
        val sql =
            """
            INSERT INTO reservation (screening_id, seat_row, seat_column, total_price, used_point, payment_method)
            VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, screeningId)
                preparedStatement.setString(2, seat.row.toString())
                preparedStatement.setInt(3, seat.column.toString().toInt())
                preparedStatement.setInt(4, totalPrice)
                preparedStatement.setInt(5, usedPoint)
                preparedStatement.setString(6, payType.name)
                preparedStatement.executeUpdate()
            }
        }
    }

    fun findByScreeningId(screeningId: Int): List<Pair<String, Int>> {
        val sql =
            """
            SELECT seat_row, seat_column                                                                                                                                                                           
            FROM reservation
            WHERE screening_id = ?
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, screeningId)
                preparedStatement.executeQuery().use { result ->
                    val seats = mutableListOf<Pair<String, Int>>()
                    while (result.next()) {
                        seats += result.getString("seat_row") to result.getInt("seat_column")
                    }
                    return seats
                }
            }
        }
    }

    fun delete(
        screeningId: Int,
        row: SeatRow,
        column: SeatColumn,
    ) {
        val sql =
            """
            DELETE FROM reservation
            WHERE screening_id = ? AND seat_row = ? AND seat_column = ?
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, screeningId)
                preparedStatement.setString(2, row.toString())
                preparedStatement.setInt(3, column.toString().toInt())
                preparedStatement.executeUpdate()
            }
        }
    }
}
