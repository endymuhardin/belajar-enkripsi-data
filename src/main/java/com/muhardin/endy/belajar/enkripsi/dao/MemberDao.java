package com.muhardin.endy.belajar.enkripsi.dao;

import org.springframework.data.repository.CrudRepository;

import com.muhardin.endy.belajar.enkripsi.entity.Member;

public interface MemberDao extends CrudRepository<Member, String> {
}
