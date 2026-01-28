package idv.alexlin7.tw.noticeboard.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import idv.alexlin7.tw.noticeboard.model.Notice;

public interface NoticeService {
    Page<Notice> findAll(Pageable pageable);

    Optional<Notice> findById(UUID id);

    Notice save(Notice notice);

    void deleteById(UUID id);
}
