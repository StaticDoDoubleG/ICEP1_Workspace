package AdminProgram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class RestaurantAccessor extends mainstream{

	public ArrayList<RestaurantData> list = new ArrayList<>();
	//public ArrayList<String> removed = new ArrayList<>();
	public String removed;

	public boolean LoadFile() {
		File DataFile = new File("Restaurant.txt");
		try {
			Scanner scan = new Scanner(DataFile, "UTF-8");
			while(scan.hasNextLine()) {
				RestaurantData data = new RestaurantData();
				data.RestaurantName = scan.nextLine(); //식당 이름
				scan.nextLine(); //식당 비밀번호이나 이 프로그램에서는 쓰이지 않음
				data.Menu = scan.nextLine(); //메뉴 문자열
				list.add(data);
			}
			scan.close();	
			return true;		
		} catch (FileNotFoundException e) {
			try {
				DataFile.createNewFile();
				System.out.println("식당 데이터 파일이 생성되었습니다.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				return true;
			} catch (IOException e1) { //이럴 경우가 없으므로 넘어가세요.
				System.out.println("식당 데이터 파일을 생성할 수 없습니다. Reservationdata.txt라는 이름을 가진 파일이 현재 디렉토리에 있는지 확인해주세요.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
				}
				return false;
			}
		}
	}
	
	public boolean Append(String name) {
		File DataFile = new File("Restaurant.txt");
		RestaurantData addData = new RestaurantData(name, "");
		list.add(addData);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DataFile, true), StandardCharsets.UTF_8));
			//FileWriter Writer = new FileWriter(DataFile, true); //파일 정보 끝자락에 추가하기
			String str = "";
			str += name + "\r\n"; //식당 이름
			str += "\r\n"; //식당 비밀번호
			str += "\r\n"; //식당 메뉴
			writer.write(str);
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean Delete(String name) {
		File DataFile = new File("Restaurant.txt");
		int amendIndex = -1;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).RestaurantName.compareTo(name) == 0) {
				list.remove(i);
				amendIndex = i;
			}
		}
		String tempStr = "", allStr = "";
		try {
			Scanner scan = new Scanner(DataFile, "UTF-8");
			try {
				//System.out.println("yo");
				while(scan.hasNextLine()) {
					tempStr = scan.nextLine(); //식당 이름 받기
					//System.out.println(tempStr);
					if(tempStr.compareTo(name) == 0) { //식당 이름이 같아
						scan.nextLine(); //비밀번호 기록 안함
						scan.nextLine(); //메뉴 기록 안함
						//System.out.println("dl");
					}else { //식당 이름이 달라
						allStr += tempStr + "\r\n";
						allStr += scan.nextLine() + "\r\n"; //비번 기록함
						allStr += scan.nextLine() + "\r\n"; //메뉴 기록함
						//System.out.println("pr");
					}
				}
				if(amendIndex >= 0) {
					mainstream.reserve.amend(amendIndex);
				}
				//mainstream.account.Amendment(name);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DataFile), StandardCharsets.UTF_8));
 //파일 정보 끝자락에 추가하기
				writer.write(allStr);
				writer.flush();
				writer.close();
				scan.close();
				return true;
			} catch (IOException e) {
				scan.close();
				return false;
			}
		}catch(FileNotFoundException e) {
			return false;
		}
	}
	
	public void subprompt() {
		Iterator<RestaurantData> ResIt = list.iterator();
		while(ResIt.hasNext()) {
			System.out.println(ResIt.next().RestaurantName);
		}
	}
	
	public int prompt() {
		String choice, addName, delName;
		while(true) {
			mainstream.clearScreen();
			//System.out.println("식당 리스트: " + list.isEmpty());
			//Iterator<RestaurantData> ResIt = list.iterator();
			System.out.println("[식당 목록 수정]");
			
			if(list.isEmpty()) {
				System.out.println("식당이 존재하지 않습니다.");
				System.out.println("");
				System.out.println("  식당 데이터 파일을 확인해주시기 바랍니다.\n");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				return -1;
			}
			subprompt();
			System.out.println();
			//while(ResIt.hasNext()) {
			//	System.out.println(ResIt.next().RestaurantName);
			//}
			System.out.println("\t1. 식당 추가");
			System.out.println("\t2. 식당 삭제");
			System.out.print("\n수행할 작업을 선택해주세요. (c 입력 시 관리자 메뉴로 돌아가기): ");
			choice = Input.nextLine();
			switch(choice) {
			case "1":
				while(true) {
					boolean resFound = false;
					System.out.print("\n[식당 추가] 추가할 식당의 이름을 입력해주세요. (c 입력 시 식당 목록 수정 프롬프트로 돌아가기): ");
					addName = Input.nextLine();
					if(addName.compareTo("c") == 0) {
						break;
					}
					if(addName.compareTo("") == 0) {
						System.out.println("\u001B[31m" + "[오류] 입력값이 잘못되었습니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}
					Iterator<RestaurantData> addIt = list.iterator();
					while(addIt.hasNext()) {
						if(addName.compareTo(addIt.next().RestaurantName) == 0) {
							resFound = true;
							break;
						}
					}
					if(resFound) {
						System.out.println("\u001B[31m" + "[오류] 해당 식당은 이미 식당 목록에 존재합니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}else {
						Append(addName);
						mainstream.clearScreen();
						System.out.println("[식당 추가 완료]");
						subprompt();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
					break;
				}
				break;
			case "2":
				while(true) {
					boolean resFound = false;
					System.out.print("\n[식당 삭제] 삭제할 식당의 이름을 입력해주세요. (c 입력 시 식당 목록 수정 프롬프트로 돌아가기): ");
					delName = Input.nextLine();
					if(delName.compareTo("c") == 0) {
						break;
					}
					Iterator<RestaurantData> delIt = list.iterator();
					while(delIt.hasNext()) {
						if(delName.compareTo(delIt.next().RestaurantName) == 0) {
							resFound = true;
							break;
						}
					}
					if(resFound) {
						Delete(delName);
						mainstream.clearScreen();
						System.out.println("[식당 삭제 완료]");
						subprompt();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						break;
					}else {
					System.out.println("\u001B[31m" + "[오류] 해당 식당은 식당 목록에 존재하지 않습니다." + "\u001B[0m");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					}
				}
				break;
			case "c":
				return -1;
			default:
				System.out.println("\u001B[31m" + "[오류] 입력값이 잘못되었습니다." + "\u001B[0m");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				continue;
			}
		}
	}
}
