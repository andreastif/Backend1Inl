package com.backend1inl.repositories;


import com.backend1inl.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {



}
