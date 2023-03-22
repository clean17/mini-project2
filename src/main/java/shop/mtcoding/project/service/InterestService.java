package shop.mtcoding.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.interest.InterestReq.InterestChangeReqDto;
import shop.mtcoding.project.dto.interest.InterestResp.InterestChangeOutDto;
import shop.mtcoding.project.model.interest.InterestRepository;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    @Transactional
    public InterestChangeOutDto 관심수정(InterestChangeReqDto iDto, Integer userId) {
        if( userId != iDto.getUserId()){
            throw new CustomApiException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            interestRepository.deleteById(userId);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!ObjectUtils.isEmpty(iDto.getInterestList())){
            try {
                interestRepository.insert(iDto);
            } catch (Exception e) {
                throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        InterestChangeOutDto interDto = interestRepository.findByInterestId(userId);
        return interDto;
    }
}
