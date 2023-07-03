package ReservationProgram;

import java.util.Scanner;

public class PersonalDataModification {
	private Scanner scan = new Scanner(System.in);
	AccountDataFileManager accData;
	String PW, temp, choice, name, tel;
	int UAI, selection;
	
	PersonalDataModification(int userAccountIndex,
			AccountDataFileManager accountData){
		this.accData = accountData;
		this.UAI = userAccountIndex;
	}
	
	public int prompt() {
		while(true) {
			System.out.print("비밀번호를 입력하세요(c 입력 시 메인 메뉴로 돌아가기): ");
			PW = scan.next();
			if(PW.compareTo("c") == 0 || PW.compareTo("C") == 0) {
				return -1;
			}
			if(PW.compareTo(accData.getAccountData(UAI).pw) != 0) {
				System.out.println("[오류] 비밀번호가 일치하지 않습니다.\n");
				continue;
			}else {
				while(true) {
					PW = "";
					System.out.println("\n[현재 개인정보]");
					System.out.println("\t아이디: " + accData.getAccountData(UAI).id);
					System.out.println("\t이름: " + accData.getAccountData(UAI).name);
					System.out.println("\t전화번호: " + accData.getAccountData(UAI).phonenumber);
					System.out.println("수정할 개인정보를 입력하세요.\n\t1. 비밀번호 \n\t2. 이름 \n\t3. 전화번호");
					System.out.print("(c 입력 시 메인 메뉴로 돌아가기): ");
					choice = scan.next();
					if(choice.compareTo("c") == 0 || choice.compareTo("C") == 0) {
						return -1;
					}
					try {
						selection = Integer.parseInt(choice);
					} catch(NumberFormatException e) {
						System.out.println("[오류] 형식에 맞지 않는 입력입니다.\n");
						continue;
					}
					choice = "";
					switch(selection) {
					case 1:
						while(true) {
							System.out.println("\n[비밀번호 수정]");
							System.out.println("새 비밀번호를 입력하세요.");
							System.out.println("비밀번호는 영문(대문자, 소문자 구분), 숫자, 특수문자 (~ . ! @ # $ % ^&*()_-+=[]\\|;:’”<><?/)로 구성된 8자 이상 16자 이하의 문자열이어야 하며, 공백, 탭, 개행을 사용할 수 없습니다.");
							System.out.println("\t3. 연속적인 숫자나 이름, 생일, 전화번호를 사용하거나 아이디와 비슷한 비밀번호를 사용하는 것은 권고하지 않습니다.");
							System.out.print("(c 입력 시 개인정보 출력 화면으로 돌아가기): ");
							PW = scan.next();
							if(PW.compareTo("c") == 0 || PW.compareTo("C") == 0) {
								break;
							}
							if(accData.isPWRight(PW)) {
								//temp = PW;
								//PW = "";
								//PW = scan.next();
								while(true) {
									System.out.print("새 비밀번호를 다시 한 번 입력하세요. (c 입력 시 개인정보 출력 화면으로 돌아가기): ");
									temp = scan.next();
									if(temp.compareTo("c") == 0 || temp.compareTo("C") == 0) {
										break;
									}
									if(PW.compareTo(temp) == 0) {
										if(accData.ModifyAccountData(UAI, 2, PW)) {
											System.out.println("비밀번호가 성공적으로 변경되었습니다.");
											break;
										}else {
											System.out.println("비밀번호가 변경되지 못했습니다.");
											break;
										}
									}else{
										System.out.println("[오류] 이전에 입력한 새 비밀번호와 일치하지 않습니다. 다시 입력해주세요.");
										continue;
									}
								}
								break;
							}else {
								System.out.println("비밀번호 규칙에 맞지 않는 입력입니다.");
								continue;
							}
						}
						break;
					case 2:
						scan.nextLine();
						while(true) {
							System.out.println("\n[이름 수정]");
							System.out.println("새 이름을 입력하세요.");
							System.out.println("이름 입력 규칙은 아래와 같습니다.");
							System.out.println("\t1. 이름은 1자 이상의 입력이어야 합니다.");
							System.out.println("\t2. 탭, 개행 입력 불가능합니다.");
							System.out.println("\t3. 특수문자를 포함할 수 없습니다.");
							System.out.print("(c& 입력 시 개인정보 출력 화면으로 돌아가기): ");
							name = scan.nextLine();
							if(name.compareTo("c&") == 0 || name.compareTo("C&") == 0) {
								break;
							}
							if(accData.ModifyAccountData(UAI, 3, name)) {
								System.out.println("이름이 성공적으로 변경되었습니다.");
								break;
							}else {
								System.out.println("[오류] 이름 규칙에 맞지 않는 입력입니다.");
								continue;
							}
						}
						break;
					case 3:
						while(true) {
							System.out.println("\n[전화번호 수정]");
							System.out.println("새 전화번호를 입력하세요.");
							System.out.println("전화번호 입력 규칙은 아래와 같습니다.");
							System.out.println("\t1. 9개 이상의 숫자와 0개 이상의 ‘-’ 문자들로만 구성되어야 합니다.");
							System.out.println("\t2. 마지막은 숫자로 끝나야 합니다.");
							System.out.println("\t3. 처음 한 숫자가 0이어야 합니다.");
							System.out.println("\t4. 두번째 숫자가 1 또는 7 또는 8이면 ‘-’의 개수에 상관없이 그 뒤에 0과 8개의 숫자가 존재하여야 합니다.");
							System.out.println("\t5. 두번째 숫자가 2면 ‘-’의 개수에 상관없이 그 뒤에 7개 혹은 8개의 숫자가 존재하여야 합니다.");
							System.out.println("\t6. 두번째 숫자가 3 또는 4 또는 5 또는 6이면 뒤에 ‘-’의 개수에 상관없이 그 뒤에 8개 혹은 9개의 숫자가 존재하여야 합니다.");
							System.out.println("\n010-1234-5678, 02-123-4567, 01012345678, 0-1-0-1-2-3-4-5-6-7-8 등\n");
							System.out.print("(c 입력 시 개인정보 출력 화면으로 돌아가기): ");
							tel = scan.next();
							if(tel.compareTo("c") == 0 || tel.compareTo("C") == 0) {
								break;
							}
							if(accData.ModifyAccountData(UAI, 4, tel)) {
								System.out.println("전화번호가 성공적으로 변경되었습니다.");
								break;
							}else {
								System.out.println("[오류] 전화번호 규칙에 맞지 않는 입력입니다.");
								continue;
							}
						}
						break;
					default:
						System.out.println("[오류] 형식에 맞지 않는 입력입니다.\n");
						break;
					}
				}
				
			}
		}
	}
	
	public int vCashPrompt() {
		int addCash;
		String temp;
		while(true) {
			System.out.print("[잔액 추가]"
					+ "현재 나의 잔액: " + accData.getAccountData(UAI).virtualCash + "원\n\n"
					+ "추가할 금액을 입력하세요. 숫자만 입력 가능합니다.\n" 
					+ "(c 입력 시 메인 메뉴로 돌아가기): ");
			temp = scan.nextLine();
			if(temp.compareTo("c") == 0) {
				return -1;
			}else {
				try {
					addCash = Integer.parseInt(temp);
					if(addCash < 0) {
						throw new NumberFormatException();
					}
					addCash += accData.getAccountData(UAI).virtualCash;
					temp = String.valueOf(addCash);
					accData.ModifyAccountData(UAI, 5, temp);
					System.out.println("잔액이 성공적으로 추가되었습니다.\n˜현재 나의 잔액: " + accData.getAccountData(UAI).virtualCash + "원");
				}catch(NumberFormatException e) {
					System.out.println("[오류] 잘못된 입력입니다. 추가하는 잔액은 0 이상의 정수여야합니다.");
					vCashPrompt();
				}
				break;
			}	
		}
		return 1;
	}
}