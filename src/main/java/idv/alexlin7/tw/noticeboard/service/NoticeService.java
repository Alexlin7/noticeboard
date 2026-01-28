package idv.alexlin7.tw.noticeboard.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import idv.alexlin7.tw.noticeboard.model.Notice;

public interface NoticeService {
    Page<Notice> findAll(Pageable pageable);

    Optional<Notice> findById(UUID id);

    @Transactional
    Notice save(Notice notice);

    @Transactional
    void deleteById(UUID id);
}
