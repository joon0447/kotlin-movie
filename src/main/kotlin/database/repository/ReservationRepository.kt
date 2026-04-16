package database.repository

import database.Database
import model.payment.PayType
import model.seat.Seat
import model.seat.SeatColumn
import model.seat.SeatRow

class ReservationRepository {
    fun saveHold(
        screeningId: Int,
        seat: Seat,
    ) {
        val sql =
            """
            INSERT INTO reservation (screening_id, seat_row, seat_column)
            VALUES (?, ?, ?)
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, screeningId)
                preparedStatement.setString(2, seat.row.toString())
                preparedStatement.setInt(3, seat.column.toString().toInt())
                preparedStatement.executeUpdate()
            }
        }
    }

    fun updatePayment(
        screeningId: Int,
        seat: Seat,
        totalPrice: Int,
        usedPoint: Int,
        payType: PayType,
    ) {
        val sql =
            """
            UPDATE reservation
            SET total_price = ?, used_point = ?, payment_method = ?
            WHERE screening_id = ? AND seat_row = ? AND seat_column = ?
            """.trimIndent()

        Database.connection().use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setInt(1, totalPrice)
                preparedStatement.setInt(2, usedPoint)
                preparedStatement.setString(3, payType.name)
                preparedStatement.setInt(4, screeningId)
                preparedStatement.setString(5, seat.row.toString())
                preparedStatement.setInt(6, seat.column.toString().toInt())
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
