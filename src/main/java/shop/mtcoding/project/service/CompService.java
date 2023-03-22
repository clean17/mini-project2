package shop.mtcoding.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.config.exception.CustomException;
import shop.mtcoding.project.dto.comp.CompReq.CompJoinReqDto;
import shop.mtcoding.project.dto.comp.CompReq.CompLoginReqDto;
import shop.mtcoding.project.dto.comp.CompReq.CompUpdateReqDto;
import shop.mtcoding.project.dto.comp.CompResp.CompLoginRespDto;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.comp.CompRepository;
import shop.mtcoding.project.util.PathUtil;
import shop.mtcoding.project.util.Sha256;

@RequiredArgsConstructor
@Service
public class CompService {

    private final CompRepository compRepository;

    @Transactional
    public CompJoinReqDto 회원가입(CompJoinReqDto compJoinReqDto) {
        Comp compPS = compRepository.findByCompEmail(compJoinReqDto.getEmail());
        if (compPS != null) {
            throw new CustomException("존재 하는 회원입니다.");
        }
        compJoinReqDto.setPassword(Sha256.encode(compJoinReqDto.getPassword()));
        Integer id = 0;
        try {
            compRepository.insert(compJoinReqDto);
            id = compJoinReqDto.getCompId();
        } catch (Exception e) {
            throw new CustomException("서버 에러가 발생 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Comp compPS2 = compRepository.findByCompId(id);
        compJoinReqDto.setCompId(compPS2.getCompId());
        compJoinReqDto.setCreatedAt(compPS2.getCreatedAt());
        return compJoinReqDto;
    }

    @Transactional(readOnly = true)
    public CompLoginRespDto 로그인(CompLoginReqDto compLoginReqDto) {
        compLoginReqDto.setPassword(Sha256.encode(compLoginReqDto.getPassword()));
        CompLoginRespDto principal = compRepository.findByEmailAndPassword(compLoginReqDto.getEmail(),
                compLoginReqDto.getPassword());
        if (principal == null) {
            throw new CustomException("이메일 혹은 패스워드가 잘못 입력 되었습니다.");
        }
        return principal;
    }

    @Transactional
    public void 회사정보수정(CompUpdateReqDto compUpdateReqDto, Integer compId) {
        if (compId != compUpdateReqDto.getCompId()) {
            throw new CustomApiException("정상적인 접근이 아닙니다.", HttpStatus.FORBIDDEN);
        }
        Comp compPS = compRepository.findByCompId(compUpdateReqDto.getCompId());
        if (compPS == null)
            throw new CustomException("존재하지 않는 회원입니다.");
        try {
            compRepository.updateByCompId(compUpdateReqDto, compId);
        } catch (Exception e) {
            throw new CustomException("서버 에러가 발생 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Comp 프로필사진수정(MultipartFile photo, Integer compId) {

        String uuidImageName = PathUtil.writeImageFile(photo);

        Comp compPS = compRepository.findByCompId(compId);
        compPS.setPhoto(uuidImageName);
        try {
            compRepository.updatePhotoById(uuidImageName, compId);
        } catch (Exception e) {
            throw new CustomException("사진 수정에 실패 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return compPS;
    }
}
