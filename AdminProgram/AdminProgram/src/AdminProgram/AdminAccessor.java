package AdminProgram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AdminAccessor extends mainstream{
	
	String ID = "", PW = "";
	
	public Boolean LoadFile() {
		File file = new File("Administrator.txt");
		String tempStr = "";
		boolean FileException = true;
		try {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(file, "UTF-8");
			while(scan.hasNextLine()) {
				Admin data = new Admin();
				ID = scan.nextLine();
				data.id = ID;
				PW = scan.nextLine();
				data.pw = PW;
				if(!Check(ID, PW)) {
					FileException = false;
					throw new FileNotFoundException();
				}
				break;
			}
			scan.close();		
			return true;		
		} catch (FileNotFoundException e) {
			try {
				if(FileException) {
					System.out.print("관리자 데이터 파일이 존재하지 않습니다. ");
					file.createNewFile();
				}else {
					System.out.print("관리자 데이터 파일 내용이 올바르지 않습니다. ");
				}
				System.out.println("파일을 갱신합니다.");
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
				//FileWriter write = new FileWriter(file);
				//tempStr = RandomAlloc();
				writer.write(tempStr);
				writer.flush();
				writer.close();
				System.out.println("관리자 데이터 파일이 갱신되었습니다.");
				return true;
			} catch (IOException e1) { //이럴 경우가 없으므로 넘어가세요.
				System.out.println("관리자 데이터 파일을 갱신할 수 없습니다. Administrator.txt라는 이름을 가진 파일이 현재 디렉토리에 있는지 확인해주세요.");
				return false;
			}
		}
	}
	
	public Boolean Check(String id, String pw) {
		if(ID(id) || PW(pw)) {
			return true;
		}
		return false;
	}
	
	public Boolean ID(String id) {
		if (id.length() > 16 || id.length() < 8) { //길이
			return false;
		}
		if (id.equals(id.replaceAll(" ", "")) == false) { //공백
			return false; 
		}
		for (int i = 0; i < id.length(); i++) {
			int c = id.charAt(i); //아이디 문자 검사
			if (i == 0) {
				if (!((c >= 65 && c <= 90) || (c >= 97 && c <= 122))) {
					return false;
				}
			} else if (!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 46 || c == 95)) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean PW(String pw) {
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
	
	public void Modify() {
		File file = new File("Administrator.txt");
		String choice, mID, mPW, tempStr = "";
		while(true) {
			mainstream.clearScreen();
			System.out.println("[관리자 계정 설정] \n현재 관리자 정보입니다.\n");
			System.out.println("아이디: " + ID);
			System.out.println("비밀번호: " + PW);
			System.out.println("\n\t1. 아이디\n\t2. 비밀번호\n");
			System.out.print("수정할 정보를 입력해주세요. (c 입력 시 메인 메뉴로 돌아가기): ");
			choice = Input.nextLine();
			switch(choice) {
			case "1":
				while(true) {
					mainstream.clearScreen();
					System.out.print("[아이디 수정] 새 아이디를 입력하세요.\n"
							+ "아이디는 공백 없이 8 ~ 16자리의 영문, 숫자, 특수문자('.', '_')로 이루어져야 하며\n"
							+ "첫 문자는 영문자로 시작해야합니다. 아이디는 중복될 수 없습니다.\n"
							+ "(c 입력 시 초기 화면으로 돌아가기.): ");
					mID = Input.nextLine();
					if(mID.compareTo("c") == 0) {
						break;
					}
					if(ID.compareTo(mID) == 0) {
						System.out.println("\u001B[31m" + "[오류] 기존에 사용하던 아이디입니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}
					if(ID(mID)) {
						System.out.println("아이디가 수정되었습니다.");
						ID = mID;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						break;
					}else {
						System.out.println("\u001B[31m" + "[오류] 아이디 규칙에 맞지 않는 입력입니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}
				}
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
					tempStr = "";
					tempStr += ID + "\r\n";
					tempStr += PW;
					writer.write(tempStr);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				while(true) {
					mainstream.clearScreen();
					System.out.println("[비밀번호 수정] 새 비밀번호를 입력하세요.");
					System.out.println("비밀번호는 영문(대문자, 소문자 구분), 숫자, 특수문자 (~ . ! @ # $ % ^&*()_-+=[]\\|;:’”<><?/)로 구성된 8자 이상 16자 이하의 문자열이어야 하며, 공백, 탭, 개행을 사용할 수 없습니다.");
					System.out.println("연속적인 숫자나 이름, 생일, 전화번호를 사용하거나 아이디와 비슷한 비밀번호를 사용하는 것은 권고하지 않습니다.");
					System.out.print("(c 입력 시 개인정보 초기 화면으로 돌아가기): ");
					mPW = Input.nextLine();
					if(mPW.compareTo("c") == 0) {
						break;
					}
					if(PW.compareTo(mPW) == 0) {
						System.out.println("\u001B[31m" + "[오류] 기존에 사용하던 비밀번호입니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}
					if(PW(mPW)) {
						while(true) {
							mainstream.clearScreen();
							String tempcheck;
							System.out.print("[비밀번호 수정] 새 비밀번호를 다시 한번 입력하세요. (c 입력 시 개인정보 출력 화면으로 돌아가기): ");
							tempcheck = Input.nextLine();
							if(tempcheck.compareTo("c") == 0) {
								break;
							}
							if(tempcheck.compareTo(mPW) == 0) {
								PW = mPW;
								System.out.println("[비밀번호 수정] 비밀번호가 성공적으로 변경되었습니다.");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
								}
								break;
							}else {
								System.out.println("\u001B[31m" + "[오류] 이전에 입력한 새 비밀번호와 일치하지 않습니다. 다시 입력해주세요." + "\u001B[0m");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
								}
								//continue;
							}
						}	
					}else {
						System.out.println("\u001B[31m" + "[오류] 비밀번호 규칙에 맞지 않는 입력입니다." + "\u001B[0m");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						continue;
					}
					break;
				}
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
					tempStr = "";
					tempStr += ID + "\r\n";
					tempStr += PW;
					writer.write(tempStr);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "c":
				return;
			}
		}
		//System.out.println("Modifies.");
	}

	
}
