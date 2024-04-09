package kr.co.seoulit.logistics.logiinfosvc.compinfo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@RestController
@RequestMapping("/compinfo/*")
public class ImgFileController {

	private static String serverUploadFolderPath = "ImgServer\\empPhoto\\";

	private static String workspaceUploadFolderPath = "C:\\물류프로젝트\\Logistics71_spring1\\src\\main\\webapp\\ImgServer\\empPhoto";

	// 😒 이미지 - 파일업로드
	@RequestMapping(value = "/imgfileupload", method = RequestMethod.POST)
	public ModelMap imgFileUpload(HttpServletRequest request, HttpServletResponse response) {

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=UTF-8");

		ModelMap map = new ModelMap();

		// 한번에 올릴 수 있는 파일 용량 : 20Mbyte 제한
		int maxSize = 1024 * 1024 * 20;

		// 톰캣에 배포된 프로젝트 경로 => (예) C:/tomcat/webapps/JHlogistics/
		String root = request.getSession().getServletContext().getRealPath("/");

		// 파일 업로드 경로 : C:/tomcat/webapps/JHlogistics/ImgServer\empPhoto/
		String uploadPath = root + serverUploadFolderPath;

		// 인코딩 타입
		String encType = "utf-8";

		MultipartRequest multi = null;

		try {
			// request , 파일저장경로 , 최대용량 , 인코딩 타입 , 중복파일명에 대한 기본 정책
			// multi 객체 생성시 바로 파일이 업로드됨

			// 톰캣 프로젝트에 저장
			multi = new MultipartRequest(request, uploadPath, maxSize, encType, new DefaultFileRenamePolicy());

			@SuppressWarnings("rawtypes")
			Enumeration files = multi.getFileNames(); // 전송한 전체 파일 이름들을 가져옴

			while (files.hasMoreElements()) {

				// 폼에서 file 태그의 이름
				String name = (String) files.nextElement();

				// 기존 업로드 폴더에 똑같은 파일이 있으면 현재 올리는 파일 이름은 바뀜 (중복 정책)
				// 파일명이 중복되는 경우, 기존 이름
				//String originalName = multi.getOriginalFileName(name);

				// 파일명이 중복될 경우 중복 정책에 의해 뒤에 1,2,3 처럼 붙어 unique하게 파일명을 생성
				// 이때 생성된 이름이 storedFileName
				String storedFileName = multi.getFilesystemName(name);

				// 전송된 파일의 타입 : MIME 타입 ( 기계어, image, HTML, text , ... )
				//String fileType = multi.getContentType(name);

				// name 에 해당하는 실제 파일을 가져옴
				File file = multi.getFile(name);

				if (file != null) {

					// 그 파일 객체의 크기
					//long fileSize = file.length();

					Path from = Paths.get(uploadPath + storedFileName);

					Path to = Paths.get(workspaceUploadFolderPath + storedFileName);

					// 톰캣 서버에 업로드된 파일을 워크스페이스의 업로드 폴더에 복사
					Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

					map.put("ImgUrl", "/" + serverUploadFolderPath + storedFileName);
					map.put("errorCode", 1);
					map.put("errorMsg", "성공");

				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (Exception e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());
		} finally {
		}
		return map;
		}
}
