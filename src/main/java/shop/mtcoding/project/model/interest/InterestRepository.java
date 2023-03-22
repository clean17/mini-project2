package shop.mtcoding.project.model.interest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.interest.InterestReq.InterestChangeReqDto;
import shop.mtcoding.project.dto.interest.InterestResp.InterestChangeOutDto;
import shop.mtcoding.project.dto.interest.InterestResp.InterestChangeRespDto;

@Mapper
public interface InterestRepository {
    public Interest findAll();
    public List<InterestChangeRespDto> findById(Integer userId);
    
    public InterestChangeOutDto findByInterestId(Integer userId);
    public int insert(
        @Param("iDto") InterestChangeReqDto iDto
    );
    public int updateById(

    );
    public int deleteById(Integer userId);
}
