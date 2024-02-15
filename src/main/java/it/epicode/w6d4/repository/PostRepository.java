package it.epicode.w6d4.repository;

import it.epicode.w6d4.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, PagingAndSortingRepository<Post,Integer> {

}
