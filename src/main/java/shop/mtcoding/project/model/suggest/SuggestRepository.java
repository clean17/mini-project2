package shop.mtcoding.project.model.suggest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestReqDto;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestUpdateReqDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestResumeOutDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestToCompRespDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestToCompRespIdDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestToUserRespDto;

@Mapper
public interface SuggestRepository {
    public void findAll();

    public SuggestResumeOutDto findBySuggestId(Integer suggestId);
    public Suggest findById(Integer suggestId);

    public SuggestToCompRespIdDto findByCompIdAndResumeId(
        @Param("compId") Integer compId,
        @Param("resumeId") Integer resumeId
    );

    public List<SuggestToCompRespDto> findAllByCompIdtoSuggest(Integer compId);
    
    public List<SuggestToUserRespDto> findAllGetOfferByUserId(Integer userId);
    
    public int insert(
        @Param("sDto") SuggestReqDto sDto
    );
    public int updateBySuggestId(
        @Param("sDto") SuggestUpdateReqDto sDto
    );

    public int deleteById(

    );
}
