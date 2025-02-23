package taodigital.interview.jpa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taodigital.interview.jpa.demo.entity.Book;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository <Book, UUID>{
}
