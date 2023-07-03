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

public class AccountAccessor extends mainstream{
	
	public ArrayList<AccountData> list = new ArrayList<>();
	
	public Boolean LoadFile() {

		File DataFile = new File("Account.txt");
		try {
			Scanner scan = new Scanner(DataFile, "UTF-8");
			while(scan.hasNextLine()) {
				AccountData data = new AccountData();
				data.id = scan.nextLine(); //예약 프로그램 사용자 아이디
				data.pw = scan.nextLine(); //예약 프로그램 사용자 비밀번호
				data.name = scan.nextLine(); //예약 프로그램 사용자 이름
				data.phonenumber = scan.nextLine(); //예약 프로그램 사용자 전화번호
				data.account = scan.nextLine();
				data.mileage = scan.nextLine(); //예약 프로그램 사용자 마일리지
				list.add(data);
			}
			scan.close();		
			return true;		
		} catch (FileNotFoundException e) {
			try {
				DataFile.createNewFile();
				System.out.println("계정 데이터 파일이 생성되었습니다.");
				return true;
			} catch (IOException e1) { //이럴 경우가 없으므로 넘어가세요.
				System.out.println("계정 데이터 파일을 생성할 수 없습니다. Reservationdata.txt라는 이름을 가진 파일이 현재 디렉토리에 있는지 확인해주세요.");
				return false;
			}
		}
	}
	
	public boolean MLCheck(int mileage) {
		if(mileage < 0) {
			return false;
		}
		return true;
	}
	
	public boolean PWCheck(String pw) {
		if (pw.length() > 16 || pw.length() < 8) { //길이
			return false;
		}
		if (pw.equals(pw.replaceAll(" ", "")) == false) { //공백
			return false;
		}
		for (int i = 0; i < pw.length(); i++) {
			int c = pw.charAt(i); //비밀번호 문자 검사
			if (!((c >= 34 && c <= 122) || c == 124 || c == 126)) {
				return false;
			}
		}
		return true;
	}
	
	public void amend(int locate) {
		File DataFile = new File("Account.txt");
		String allStr = "";
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DataFile), StandardCharsets.UTF_8));
			//FileWriter write = new FileWriter(DataFile);
			allStr = "";
			for(int i = 0;  i < list.size(); i++) {
				allStr += list.get(i).id + "\r\n";
				allStr += list.get(i).pw + "\r\n";
				allStr += list.get(i).name + "\r\n";
				allStr += list.get(i).phonenumber + "\r\n";
				allStr += list.get(i).account + "\r\n";
				allStr += list.get(i).mileage + "\r\n";
			}
			writer.write(allStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void subprompt() {
		Iterator<AccountData> AccIt = list.iterator();
		while(AccIt.hasNext()) {
			System.out.println(AccIt.next().id);
		}
	}
	
	public int prompt() {
		//File DataFile = new File("Account.txt");
		String amendID, choice;
		int amendIndex = 0;
		boolean accountFound = false;
		while(true) {
			mainstream.clearScreen();
			accountFound = false;
			System.out.println("[계정 데이터 파일 수정]");
			if(list.isEmpty()) {
				System.out.println("예약 프로그램 사용자 계정이 존재하지 않습니다.");
				System.out.println("");
				System.out.println("  계정 데이터 파일을 확인해주시기 바랍니다.\n");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				return -1;
			}
			subprompt();
			System.out.print("\n수정할 계정의 아이디를 입력해주세요. (c 입력 시 관리자 메뉴로 돌아가기): ");
			amendID = Input.nextLine();
			if(amendID.compareTo("c") == 0) {
				break;
			}
			for(int i = 0; i < list.size(); i++) {
				if(amendID.compareTo(list.get(i).id) == 0) {
					amendIndex = i;
					accountFound = true;
					break;
				}
			}
			if(accountFound) {
				while(true) {
					mainstream.clearScreen();
					System.out.println("[" + list.get(amendIndex).id + "의 계정 정보]");
					System.out.println("\t아이디: " + list.get(amendIndex).id);
					System.out.println("\t비밀번호: " + list.get(amendIndex).pw);
					System.out.println("\t이름: " + list.get(amendIndex).name);
					System.out.println("\t전화번호: " + list.get(amendIndex).phonenumber);
					System.out.println("\t가상잔액: " + list.get(amendIndex).account);
					System.out.println("\t마일리지: " + list.get(amendIndex).mileage);
					System.out.println("\n수정가능한 계정 정보\n\n\t1. 비밀번호\n\t2. 마일리지\n");
					System.out.print("수정할 계정 정보의 번호를 입력하세요. (c 입력 시 계정 선택 화면으로 돌아가기): ");
					choice = Input.nextLine();
					switch(choice) {
					case "1":
						while(true) {
							mainstream.clearScreen();
							System.out.print("[비밀번호 수정] "
									+ "새 비밀번호를 입력하세요.\r\n"
									+ "비밀번호는 영문(대문자, 소문자 구분), 숫자, 특수문자 (~ . ! @ # $ % ^&*()_-+=[]\\|;:’”<><?/)로 구성된 8자 이상 16자 이하의 문자열이어야 하며, 공백, 탭, 개행을 사용할 수 없습니다.\r\n"
									+ "연속적인 숫자나 이름, 생일, 전화번호를 사용하거나 아이디와 비슷한 비밀번호를 사용하는 것은 권고하지 않습니다.\r\n"
									+ "(c 입력 시 계정 수정 메뉴로 돌아가기): ");
							choice = Input.nextLine();
							if(choice.compareTo("c") == 0) {
								break;
							}else {
								if(choice.compareTo(list.get(amendIndex).pw) == 0) {
									System.out.println("\u001B[31m" + "[오류] 기존 비밀번호와 동일합니다." + "\u001B[0m");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
									continue;
								}else if(PWCheck(choice)) {
									list.get(amendIndex).pw = choice;
									System.out.println("[비밀번호 수정] 비밀번호가 갱신되었습니다.");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
								}else {
									System.out.println("\u001B[31m" + "[오류] 비밀번호 규칙에 맞지 않는 입력입니다." + "\u001B[0m");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
									continue;
								}				
							}
							break;
						}
						amend(amendIndex);
						continue;
					case "2":
						while(true) {
							mainstream.clearScreen();
							System.out.print("[마일리지 수정] 해당 사용자의 마일리지 값을 입력해주세요.\n(c 입력 시 계정 수정 메뉴로 돌아가기): ");
							choice = Input.nextLine();
							if(choice.compareTo("c") == 0) {
								break;
							}else {
								try {
									if(MLCheck(Integer.parseInt(choice))) {
										list.get(amendIndex).mileage = choice;
										System.out.println("[마일리지 수정] 마일리지 값이 " + choice + "으로 갱신되었습니다.");
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
										}
									}else {
										System.out.println("\u001B[31m" + "[오류] 마일리지 형식에 맞지 않는 입력입니다." + "\u001B[0m");
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
										}
										continue;
									}	
								}catch(NumberFormatException n) {
									System.out.println("\u001B[31m" + "[오류] 마일리지 형식에 맞지 않는 입력입니다." + "\u001B[0m");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
									continue;
								}	
							}
							break;
						}
						amend(amendIndex);
						continue;
					case "c":
						break;
					default:
						continue;
					}
					break;
				}
			}else {
				System.out.println("\u001B[31m" + "[오류] 목록에 입력하신 아이디와 일치하는 아이디가 존재하지 않습니다." + "\u001B[0m");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		return -1;
	}
}
