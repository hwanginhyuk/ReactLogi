package kr.co.seoulit.logistics.busisvc.logisales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.seoulit.logistics.busisvc.logisales.service.LogisalesService;
import kr.co.seoulit.logistics.busisvc.logisales.to.ContractDetailTO;
import kr.co.seoulit.logistics.busisvc.logisales.to.ContractInfoTO;
import kr.co.seoulit.logistics.busisvc.logisales.to.ContractTO;
import kr.co.seoulit.logistics.busisvc.logisales.to.EstimateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/logisales/*")

public class ContractController {

	@Autowired
	private LogisalesService logisalesService;

	ModelMap map=null;

	private static Gson gson = new GsonBuilder().serializeNulls().create();

	// 🏢수주조회 - 조회
	@RequestMapping(value="/contract/list", method=RequestMethod.GET)
	public ModelMap searchContract(
			@RequestParam("startDate") String startDate,
			@RequestParam("searchCondition") String searchCondition,
			@RequestParam("endDate") String endDate,
			@RequestParam("customerCode") String customerCode
		) {


		map = new ModelMap();

		try {
			ArrayList<ContractInfoTO> contractInfoTOList = null;

			contractInfoTOList = logisalesService.getContractList(searchCondition, startDate ,endDate ,customerCode);
			map.put("gridRowJson", contractInfoTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	// 🏢수주조회 - 상세조회
	@RequestMapping(value="/contractdetail/list" , method=RequestMethod.GET)
	public ModelMap searchContractDetail(@RequestParam("contractNo") String contractNo) {

		map = new ModelMap();

		try {
			ArrayList<ContractDetailTO> contractDetailTOList = logisalesService.getContractDetailList(contractNo);
			map.put("gridRowJson", contractDetailTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	// 🚩견적조회
	@RequestMapping(value= "/estimate/list/contractavailable", method=RequestMethod.GET)
	public ModelMap searchEstimateInContractAvailable(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {

		map = new ModelMap();

		try {

			ArrayList<EstimateTO> estimateListInContractAvailable = logisalesService.getEstimateListInContractAvailable(startDate, endDate);
			map.put("gridRowJson", estimateListInContractAvailable);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
	// 🚩수주등록
	@RequestMapping(value="/contract/new" , method=RequestMethod.POST)
	public ModelMap addNewContract(@RequestBody Map<String, Object> params) {

		map = new ModelMap();

		try {
			map = logisalesService.addNewContract(params);
			System.out.println("수주등록 map : "+ map);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	// 🚩견적취소 - 미구현
	@RequestMapping(value="/estimate/cancel" , method=RequestMethod.PUT)
	public ModelMap cancleEstimate(@RequestParam("estimateNo") String estimateNo) {

		map = new ModelMap();

		try {
			logisalesService.changeContractStatusInEstimate(estimateNo, "N");

			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
			map.put("cancledEstimateNo", estimateNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}
}
