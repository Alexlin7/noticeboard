package idv.alexlin7.tw.noticeboard.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.alexlin7.tw.noticeboard.model.Notice;
import idv.alexlin7.tw.noticeboard.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Optional<Notice> findById(UUID id) {
        return noticeRepository.findById(id);
    }

    @Transactional
    @Override
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        noticeRepository.deleteById(id);
    }
}
