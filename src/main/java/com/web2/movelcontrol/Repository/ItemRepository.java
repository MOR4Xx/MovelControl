package com.web2.movelcontrol.Repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.movelcontrol.Model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{


}
=======
import com.web2.movelcontrol.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
>>>>>>> 809fa8c7a5ef195f49b64c91eff9de0d865f6066
