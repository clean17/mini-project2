package shop.mtcoding.project.model.comp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.comp.CompReq.CompJoinReqDto;
import shop.mtcoding.project.dto.comp.CompReq.CompUpdateReqDto;
import shop.mtcoding.project.dto.comp.CompResp.CompLoginRespDto;
import shop.mtcoding.project.dto.comp.CompResp.CompWriteJobsRespDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsUpdateReqDto;

@Mapper
public interface CompRepository {
        public List<Comp> findAll();

        public Comp findByCompEmail(@Param("email") String email);

        public CompLoginRespDto findByEmailAndPassword2(@Param("email") String email,
                        @Param("password") String password);

        public Comp findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

        public CompWriteJobsRespDto findByIdToJobs(Integer CompId);

        public Comp findByCompId(@Param("compId") Integer compId);

        public int insert(@Param("uDto") CompJoinReqDto uDto);

        public int updateById(@Param("cDto") JobsUpdateReqDto cDto);

        public int deleteById();

        public int updatePhotoById(
                        @Param("photo") String photo,
                        @Param("compId") Integer compId);

        public int updateByCompId(
                        @Param("compUpdateReqDto") CompUpdateReqDto compUpdateReqDto,
                        @Param("compId") Integer compId);

        public Comp findByCompidAndPassword(@Param("compId") Integer compId, @Param("password") String password);
}
