package idv.alexlin7.tw.noticeboard.service;

import idv.alexlin7.tw.noticeboard.model.Notice;
import idv.alexlin7.tw.noticeboard.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

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
