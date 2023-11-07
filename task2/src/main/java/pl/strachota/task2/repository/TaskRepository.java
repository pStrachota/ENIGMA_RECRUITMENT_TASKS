package pl.strachota.task2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.strachota.task2.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>,
        JpaSpecificationExecutor<Task>, JpaRepository<Task, Long> {

    List<Task> findAllByAssignedUsers_Id(Long id);

}