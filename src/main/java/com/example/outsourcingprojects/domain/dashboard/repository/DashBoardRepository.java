package com.example.outsourcingprojects.domain.dashboard.repository;

import com.example.outsourcingprojects.domain.entity.DashBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DashBoardRepository extends JpaRepository<DashBoard, Long> {

    @Query("SELECT d FROM DashBoard d WHERE d.id = (SELECT MAX(d2.id) FROM DashBoard d2)")
    DashBoard findLatestDashBoard();

}