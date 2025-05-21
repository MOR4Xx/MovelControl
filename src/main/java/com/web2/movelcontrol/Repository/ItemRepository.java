package com.web2.movelcontrol.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.movelcontrol.Model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{


}