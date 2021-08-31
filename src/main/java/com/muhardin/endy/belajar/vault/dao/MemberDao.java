package com.muhardin.endy.belajar.vault.dao;

import com.muhardin.endy.belajar.vault.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberDao extends CrudRepository<Member, String> {
}
