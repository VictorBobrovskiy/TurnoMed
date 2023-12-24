package com.digsol.turnomed.model

import com.digsol.turnomed.dto.UsersDTO
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.sql.Timestamp
import java.util.Collections
import java.util.Set

class Users : User {

        companion object {
                private const val serialVersionUID = -8136811209625422423L
        }
        var id: Integer? = null
        var firstName: String? = null
        var lastName: String? = null
        var email: String? = null
        var password: String? = null
        var regStatus: Integer? = null
        var regDate: Timestamp? = null
        var confirmDate: Timestamp? = null

        constructor(usersDTO: UsersDTO) : super(
        usersDTO.email,
        usersDTO.password,
        Collections.emptyList()
        ) {
                this.firstName = usersDTO.firstName
                this.lastName = usersDTO.lastName
                this.email = usersDTO.email
                this.password = usersDTO.password
                this.regStatus = usersDTO.regStatus
                this.regDate = usersDTO.regDate
                this.confirmDate = usersDTO.confirmDate
        }

        constructor(
        id: Integer?,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        password1: String?
        ) : super(email, password1, Collections.emptyList()) {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password1
        }

        constructor(
        id: Integer?,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        password1: String?,
        regStatus: Integer?
        ) : super(email, password1, Collections.emptyList()) {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password1
                this.regStatus = regStatus
        }

        constructor(
        id: Integer?,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        password1: String?,
        regStatus: Integer?,
        regDate: Timestamp?,
        confirmDate: Timestamp?
        ) : super(email, password1, Collections.emptyList()) {
                this.id = id
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password1
                this.regStatus = regStatus
                this.regDate = regDate
                this.confirmDate = confirmDate
        }

        constructor(username: String?, password: String?, authorities: Collection<out GrantedAuthority>?) :
        super(username, password, authorities)

        constructor(user: Users, grants: Set<GrantedAuthority>?) : super(
                user.username,
                user.password,
                grants
        ) {
                this.id = user.id
                this.firstName = user.firstName
                this.lastName = user.lastName
                this.email = user.email
                this.password = user.password
        }

        override fun toString(): String {
                return "Users{" +
                "id=$id, " +
                "firstName='$firstName', " +
                "lastName='$lastName', " +
                "email='$email', " +
                "password='$password'}"
        }

        fun toDto(): UsersDTO {
                return UsersDTO(firstName, lastName, email, null)
        }
}
