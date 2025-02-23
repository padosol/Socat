package socat.postservice.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
class CategoryEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryId: Long,
    var categoryName: String,
) {

}