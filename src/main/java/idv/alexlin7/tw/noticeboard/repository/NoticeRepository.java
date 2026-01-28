package idv.alexlin7.tw.noticeboard.repository;

import idv.alexlin7.tw.noticeboard.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, UUID> {

}
