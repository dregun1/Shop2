package com.example.shop_17.member.repository;

import com.example.shop_17.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String eamil);

}
