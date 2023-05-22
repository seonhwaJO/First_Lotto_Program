package com.multicampus.lottomachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import com.multicampus.lottomachine.domain.CustomerLottoVO;
import com.multicampus.lottomachine.domain.ResultLottoVO;
import com.multicampus.lottomachine.domain.StaticsWinningVO;
import com.multicampus.lottomachine.domain.UserVO;
import com.multicampus.lottomachine.domain.WinningLottoVO;
import com.multicampus.lottomachine.exception.NumberOutOfBoundException;

// Controller가 요청한 서비스를 처리하는 서비스
// TODO 2단계 : 당첨 개수를 받아서 상금을 처리(금액 및 수익률)하는 메서드 추가
public class LottoMachineService {
	private CustomerLottoVO customVo;		//사용자 값을 저장하는 객체
	private WinningLottoVO winningVO;		//당첨 정보를 저장하는 객체
	private ResultLottoVO resultVO;				//당첨 결과를 저장하는 객체
	private StaticsWinningVO winningStatics;	//해당 회차의 통계 정보를 저장하는 객체
	private ArrayList<StaticsWinningVO> winningList = new ArrayList<StaticsWinningVO>();	//사용자의 당첨정보가 누적될 리스트
	private ArrayList<CustomerLottoVO> customList = new ArrayList<>();	//사용자 입력이 누적될 리스트
	private ArrayList<ResultLottoVO> resultList =new ArrayList<>();
	private int roundNumber;	//회차 정보
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//사용자 입력받기
	
	//TODO 2단계 : 보너스 번호 추가
	public void checkLottoNumber() {	//당첨번호 생성후 당첨 여부 저장
		winningVO = new WinningLottoVO();	//로또 번호 만들기 
		for(int i=0;i<customList.size();i++) {
			resultVO = new ResultLottoVO(customList.get(i).getList(), winningVO.getList());	//당첨정보객체 생성
			resultList.add(resultVO);
		}
		this.roundNumber+=1;		//회차 회수를 올린다. 
		for(int i=0;i<customList.size();i++) {
			winningStatics =new StaticsWinningVO(this.roundNumber,customList.get(i).getAutoInfo(),
					customList.get(i).getList(),winningVO.getList(),resultList.get(i).getList());	//회차 당첨정보 객체 생성
			winningList.add(winningStatics);	//리스트에 회차정보 추가
		}
	}
	
	public TreeSet<Integer> getCustomList() { return customVo.getList(); }	//입력된 사용자 값을 가져오기
	public TreeSet<Integer> getWinningList(){ return winningVO.getList();	}	//당첨 정보 가져오기
	public TreeSet<Integer> getResultList() {	return resultVO.getList(); }	//사용자 이번 회차 당첨정보 가져오기
	public int getResultIntNumber() {	return resultVO.getResultNumber(); }	//당첨 갯수 가져오기
	public ArrayList<CustomerLottoVO>  getCustomArrayList(){return customList;}
	public ArrayList<StaticsWinningVO> getWinningStaticsInfo() { 	// 당첨 통계 가져오기
		if(roundNumber==0) {	// 회차가 0이면 게임을 한 적이 없음
			throw new NullPointerException("게임 내역이 없습니다.");
		}
		return this.winningList; 
	}
	
	public int setUsingPaper(UserVO userVO) throws NumberOutOfBoundException, IOException{
		int usingLottoPaper = Integer.parseInt(br.readLine());
		if(usingLottoPaper>userVO.getLottoPaper()) {
			throw new NumberOutOfBoundException("현재 보유 로또보다 많습니다.");
		}
		return usingLottoPaper;
	}
	
	public void setCustomList() throws NumberOutOfBoundException, IOException{ 	//사용자 입력된 로또 값을 데이터에 입력
			String inputCustomerNumber = br.readLine();
			customVo = new CustomerLottoVO(inputCustomerNumber); 
			customList.add(customVo);
	}
	//TODO 2단계 :  이 부분에서 돈이 빠져나간다.
	public boolean isRunnningGames()  throws IOException{	//게임을 계속 진행할 것인지 묻는 메서드 
		String isRunning = br.readLine();	//진행여부를 물어봄
		if(isRunning.toLowerCase().equals("yes")||isRunning.toLowerCase().equals("y")) {	//진행여부가 yes인 경우에만 true반환
			return true;
		}
		return false;
	}

	public void buyLottoPaper(UserVO userInfo) throws NumberOutOfBoundException,IOException {
			int lottoPaper= 0;
			lottoPaper = 	Integer.parseInt(br.readLine());
			if(userInfo.getUserMoney()<(lottoPaper*1000)) {
				throw new NumberOutOfBoundException("보유금액이 구매금액보다 작습니다. 다시 입력해주세요");
			}
			userInfo.setLottoPaper(lottoPaper);
			userInfo.setUserMoney(userInfo. getUserMoney()- (lottoPaper*1000));
	}
}
