package com.dongoh.project.springboot.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimpleNoticeRepository extends JpaRepository<SimpleNotice, Long> {

    List<SimpleNotice> findByLanguageAndIsActivated(String language, boolean isActivated);
    SimpleNotice findByGroupNumAndLanguage(Long groupNum, String Language);

}
