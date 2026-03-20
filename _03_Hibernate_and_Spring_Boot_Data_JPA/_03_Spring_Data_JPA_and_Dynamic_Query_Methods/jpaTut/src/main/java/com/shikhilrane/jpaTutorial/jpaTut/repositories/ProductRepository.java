package com.shikhilrane.jpaTutorial.jpaTut.repositories;

import com.shikhilrane.jpaTutorial.jpaTut.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // I. Derived Query

    List<ProductEntity> findByTitle(String title);      // This method is in JpaTutApplicationTests.java
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);      // This method is in JpaTutApplicationTests.java (Used After variable here)
    List<ProductEntity> findByQuantityAndPrice (Integer qty, BigDecimal price); // This method is in JpaTutApplicationTests.java (Used And variable here)
    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan (Integer qty, BigDecimal price); // This method is in JpaTutApplicationTests.java (Used And variable here)
    List<ProductEntity> findByTitleLike (String title); // This method is in JpaTutApplicationTests.java (Used Like variable here)
    List<ProductEntity> findByTitleContaining (String title); // This method is in JpaTutApplicationTests.java (Used Containing variable here)
    List<ProductEntity> findByTitleContainingIgnoringCase (String title); // This method is in JpaTutApplicationTests.java (Used Containing variable and will ignore the case)
//    Optional<ProductEntity> findByTitleAndPrice (String title, BigDecimal price); // This method is in JpaTutApplicationTests.java (getting single entity) (Commented because we will writing our own jpql custom queries)




    // Queries according to rules

    // A. findBy, readBy, queryBy, getBy
    // 1. findBy → Used to fetch records from the database based on given conditions.
    // List<ProductEntity> findByTitle(String title); (commented because we already wrote query for this above)

    // 2. readBy → Works same as findBy, used to read/fetch records based on conditions.
    List<ProductEntity> readByTitle(String title);

    // 3. queryBy → Used to execute a derived query to retrieve records.
    List<ProductEntity> queryByTitle(String title);

    // 4. getBy → Retrieves entity/entities matching the given condition.
    List<ProductEntity> getByTitle(String title);

    // 5. searchBy → Used to search and return records matching the condition.
    List<ProductEntity> searchByTitle(String title);

    // 6. streamBy → Returns the result as a Java Stream for processing large datasets efficiently.
    Stream<ProductEntity> streamByPriceGreaterThan(BigDecimal price);

    // 7. existsBy → Checks whether a record exists for the given condition and returns boolean.
    boolean existsBySku(String sku);

    // 8. countBy → Returns the count of rows matching the given condition.
    long countByTitle(String title);

    // 9. deleteBy → Deletes records matching the condition and returns number of rows deleted.
    long deleteBySku(String sku);

    // 10. removeBy → Same as deleteBy, removes records matching the condition.
    long removeByTitle(String title);


    // B. Limiting
    // 1. findFirstBy → Returns the first record that matches the given condition.
    ProductEntity findFirstByTitle(String title);

    // 2. findFirst3By → Returns the first 3 records that match the given condition.
    List<ProductEntity> findFirst3ByPrice(BigDecimal price);

    // 3. findTop5By → Returns the top 5 records that match the given condition (based on database order).
    List<ProductEntity> findTop5ByQuantity(Integer quantity);


    // C. Distinct → Returns only unique records (removes duplicate results based on the query).
    List<ProductEntity> findDistinctByTitle(String title);


    // D. AND/OR Conditions
    // 1. AND (Both conditions must be matched)
//    List<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);  (Commented because we wrote same query below)

    // 2. OR (Any conditions should be matched)
    List<ProductEntity> findByTitleOrSku(String title, String sku);

    // Combine Query
    List<ProductEntity> findTop5DistinctByTitleAndPrice(String title, BigDecimal price);






    // II. JPQL
    @Query("SELECT e from ProductEntity e where e.title=:title and e.price=:price") // 2nd way of writing custom jpql queries
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    // JPQL → Fetch product by SKU
    @Query("SELECT e FROM ProductEntity e WHERE e.sku = :sku")
    Optional<ProductEntity> findBySku(String sku);

    // JPQL → Fetch products having price greater than given price
    @Query("SELECT e FROM ProductEntity e WHERE e.price > :price")
    List<ProductEntity> findProductsWithPriceGreaterThan(BigDecimal price);

    // JPQL → Fetch products by title
    @Query("SELECT e FROM ProductEntity e WHERE e.title = :title")
    List<ProductEntity> findProductsByTitle(String title);

    // JPQL → Fetch products with quantity less than given value
    @Query("SELECT e FROM ProductEntity e WHERE e.quantity < :quantity")
    List<ProductEntity> findProductsWithLowStock(Integer quantity);

    // JPQL → Fetch products created after a specific date
    @Query("SELECT e FROM ProductEntity e WHERE e.createdAt > :date")
    List<ProductEntity> findProductsCreatedAfter(LocalDateTime date);





    // III. Native Query → Executes SQL directly on the database table
    @Query(value = "SELECT * FROM product_table WHERE title=? AND price=?", nativeQuery = true)
    Optional<ProductEntity> findByTitleAndPriceNative(String title, BigDecimal price);

}
