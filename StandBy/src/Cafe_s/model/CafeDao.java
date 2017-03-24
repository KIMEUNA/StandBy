package Cafe_s.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Cafe_s.model.service.EmployeeVO;
import Cafe_s.model.service.StockVO;
import service.CoffeeVO;
import service.MemberVO;
import service.MySbVO;
import service.OrderVO;

public class CafeDao {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
	
	public CafeDao() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "standby", "sb");
//			con = DriverManager.getConnection("jdbc:oracle:thin:@172.20.10.3:1521:orcl2", "standby", "sb");
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// select
	public ArrayList<MemberVO> m_select() {
		
		ArrayList<MemberVO> res = new ArrayList<>();

		try {
			sql = "select * from Member";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				MemberVO vo = new MemberVO();
				
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("id"));
				vo.setPass(rs.getString("pass"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setBrithDate(rs.getDate("birth"));
				vo.setJoinDate(rs.getDate("joindate"));
				vo.setBlock(rs.getInt("block"));
				vo.setGrade(rs.getInt("grade"));
				vo.setPasshint(rs.getString("passhint"));
				vo.setHintanswer(rs.getString("hintanswer"));
				vo.setPoint(rs.getInt("point"));
				

				res.add(vo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	// 아이디찾기
	public MemberVO idfind(MemberVO vo) {
		
		try {
			sql = "select id from member where phone='" +vo.getPhone()+"' and name = '"+vo.getName()+"'";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				vo.setId(rs.getString("id"));
				
			}
			
			System.out.println(vo.getId());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
	
	// 비밀번호 찾기
		public MemberVO pwfind(MemberVO vo) {

			try {
				sql = "select pass "
						+ "from member "
						+ "where id='"+ vo.getId() +"' and name = '"+vo.getName()+"'";
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					vo.setPass(rs.getString("pass"));
				}
				
				System.out.println("비번 "+vo.getPass());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close();
			}
			return vo;
		}
		// 비밀번호 찾기
		public EmployeeVO pwfind(EmployeeVO vo) {

			try {
				sql = "select pass "
						+ "from Employee "
						+ "where id='"+ vo.getId() +"' and name = '"+vo.getE_name()+"'";
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					vo.setE_pass(rs.getString("pass"));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close();
			}
			return vo;
		}
	
	public ArrayList<MemberVO> block_select() {
		
		ArrayList<MemberVO> res = new ArrayList<>();

		try {

			sql = "select * from Member where block = 1";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				MemberVO vo = new MemberVO();
				
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("id"));
				vo.setPass(rs.getString("pass"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setBrithDate(rs.getDate("birth"));
				vo.setJoinDate(rs.getDate("joindate"));
				vo.setBlock(rs.getInt("block"));
				vo.setGrade(rs.getInt("grade"));
				vo.setPasshint(rs.getString("passhint"));
				vo.setHintanswer(rs.getString("hintanswer"));
				vo.setPoint(rs.getInt("point"));
				

				res.add(vo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	
	public ArrayList<EmployeeVO> e_select() {
		ArrayList<EmployeeVO> res = new ArrayList<>();

		try {

			sql = "select * from Employee";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				EmployeeVO evo = new EmployeeVO();
				
				evo.setId(rs.getString("id"));
				evo.setE_name(rs.getString("name"));
				evo.setE_pass(rs.getString("pass"));
				evo.setGrade(rs.getString("grade"));
				evo.setPay(rs.getInt("pay"));
				evo.setDate(rs.getDate("joindate"));

				res.add(evo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}

	public ArrayList<CoffeeVO> c_select() {
		ArrayList<CoffeeVO> res = new ArrayList<>();

		try {
			sql = "select * from COFFEE order by sort";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				CoffeeVO cvo = new CoffeeVO();

				cvo.setC_seq(rs.getInt("seq"));
				cvo.setC_name(rs.getString("name"));
				cvo.setCate(rs.getString("cate"));
				cvo.setGroup(rs.getString("cate2"));
				cvo.setPrice(rs.getInt("price"));
				cvo.setImg(rs.getString("img"));

				res.add(cvo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	public ArrayList<OrderVO> s_select(Date start, Date end, int sch) {
		ArrayList<OrderVO> res = new ArrayList<>();
		
		try {
			if(sch == 0)				// 기간 검색
				sql = "select * "
						+ "from tb_order "
						+ "where regdate >= to_date('"+sdf.format(start)+"', 'YYYYMMDD') and regdate < to_date('"+sdf.format(end)+"', 'YYYYMMDD')+1"
						+ " order by regdate desc";
			
			else if (sch == 1)			// 기간별 판매갯수 검색
				sql = "select cate, cate1, name ,sum(ea) as cnt "
						+ "from tb_order "
						+ "where regdate >= to_date('"+sdf.format(start)+"', 'YYYYMMDD') and regdate < to_date('"+sdf.format(end)+"', 'YYYYMMDD')+1"
						+ " group by cate, cate1, name "
						+ "order by cate desc, cate1, sum(ea) desc";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrderVO svo = new OrderVO();
				if (sch == 0) {
					svo.setSeq(rs.getInt("seq"));
					svo.setUser_id(rs.getString("user_id"));
					svo.setCate(rs.getString("cate"));
					svo.setGroup(rs.getString("cate1"));
					svo.setName(rs.getString("name"));
					svo.setEa(rs.getInt("ea"));
					svo.setPrice(rs.getInt("price"));
					svo.setOrder(rs.getInt("n_order"));
					svo.setRegDate(rs.getDate("regdate"));
				}
				else if(sch == 1){
					svo.setCate(rs.getString("cate"));
					svo.setGroup(rs.getString("cate1"));
					svo.setName(rs.getString("name"));
					svo.setEa(rs.getInt("cnt"));
				}
				res.add(svo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	public ArrayList<StockVO> stock_select(Date start, Date end, int flag) {
		
		ArrayList<StockVO> res = new ArrayList<>();
		String[] arr = {"입고", "출고"};
		
		try {
			if(flag != 0)
				sql = "select * from Stock "
						+ "where regdate >= to_date('"+sdf.format(start)+"', 'YYYYMMDD') and regdate < to_date('"+sdf.format(end)+"', 'YYYYMMDD')+1"
						+ " and stat = '"+arr[flag-1]
						+ "' order by regdate desc, item_id";
			
			else
				sql = "select item_id, item_name, sum(item_in) as item_in, "
						+ "sum(item_out) as item_out, sum(item_in)-sum(item_out) as total "
						+ "from STOCK "
						+ "where regdate >= to_date('"+sdf.format(start)+"', 'YYYYMMDD') and regdate < to_date('"+sdf.format(end)+"', 'YYYYMMDD')+1 "
						+ "group by item_id, item_name "
						+ "order by item_id";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				StockVO svo = new StockVO();
				
				svo.setItem_id(rs.getInt("item_id"));
				svo.setItem(rs.getString("item_name"));
				svo.setIn(rs.getInt("item_in"));
				svo.setOut(rs.getInt("item_out"));
				
				if(flag != 0){
					svo.setStat(rs.getString("stat"));
					svo.setRegDate(rs.getDate("regdate"));
				}else
					svo.setTotal(rs.getInt("total"));
				
				res.add(svo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	public ArrayList<OrderVO> order_select(MemberVO vo) {
		ArrayList<OrderVO> res = new ArrayList<>();

		try {
			sql = "select * from tb_order where user_id = '"+vo.getId()+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				OrderVO svo = new OrderVO();

				svo.setSeq(rs.getInt("seq"));
				svo.setUser_id(rs.getString("user_id"));
				svo.setName(rs.getString("name"));
				svo.setEa(rs.getInt("ea"));
				svo.setPrice(rs.getInt("price"));
				svo.setOrder(rs.getInt("n_order"));
				svo.setRegDate(rs.getDate("regdate"));
				
				res.add(svo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	// 나만의 스탠바이 셀렉트
	public ArrayList<MySbVO> MySb_select(MemberVO vo) {
		ArrayList<MySbVO> res = new ArrayList<>();

		try {
			sql = "select * from mystandby where user_id = '"+vo.getId()+"'"
					+ "order by regdate";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				MySbVO msbvo = new MySbVO();

				msbvo.setSeq(rs.getInt("seq"));
				msbvo.setTitle(rs.getString("title"));
				msbvo.setUser_id(rs.getString("user_id"));	
				msbvo.setName(rs.getString("name"));
				msbvo.setIce(rs.getInt("ice"));
				msbvo.setHot(rs.getInt("hot"));
				msbvo.setShot(rs.getInt("shot"));
				msbvo.setJavachip(rs.getInt("javachip"));
				msbvo.setCaramelsyrup(rs.getInt("caramelsyrup"));
				msbvo.setChocosyrup(rs.getInt("chocosyrup"));
				msbvo.setWhippingcream(rs.getInt("whippingcream"));
				msbvo.setPrice(rs.getInt("price"));
				msbvo.setRegdate(rs.getDate("regdate"));
				msbvo.setLike_cnt(rs.getInt("like_cnt"));	
				msbvo.setSharing(rs.getInt("SHARING"));
				msbvo.setImg(rs.getString("img"));
				
				res.add(msbvo);
			}
			System.out.println("res: "+res.size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}	

	// 너와나의 스탠바이 셀렉트
	public ArrayList<MySbVO> OurSb_select() {
		ArrayList<MySbVO> res = new ArrayList<>();

		try {

			sql = "select * from mystandby where sharing = 1 order by like_cnt desc";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				MySbVO msbvo = new MySbVO();

				msbvo.setSeq(rs.getInt("seq"));
				msbvo.setTitle(rs.getString("title"));
				msbvo.setUser_id(rs.getString("user_id"));
				msbvo.setName(rs.getString("name"));
				msbvo.setIce(rs.getInt("ice"));
				msbvo.setHot(rs.getInt("hot"));
				msbvo.setShot(rs.getInt("shot"));
				msbvo.setJavachip(rs.getInt("javachip"));
				msbvo.setCaramelsyrup(rs.getInt("caramelsyrup"));
				msbvo.setChocosyrup(rs.getInt("chocosyrup"));
				msbvo.setWhippingcream(rs.getInt("whippingcream"));
				msbvo.setPrice(rs.getInt("price"));
				msbvo.setRegdate(rs.getDate("regdate"));
				msbvo.setLike_cnt(rs.getInt("like_cnt"));
				msbvo.setSharing(rs.getInt("SHARING"));
				msbvo.setImg(rs.getString("img"));

				res.add(msbvo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}

		// 나만의 스탠바이 셀렉트
	public MySbVO orderSb_select(MySbVO vo) {

		MySbVO msbvo = new MySbVO();

		try {
			sql = "select * from mystandby where title = '" + vo.getTitle() + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				msbvo.setSeq(rs.getInt("seq"));
				msbvo.setTitle(rs.getString("title"));
				msbvo.setUser_id(rs.getString("user_id"));
				msbvo.setName(rs.getString("name"));
				msbvo.setIce(rs.getInt("ice"));
				msbvo.setHot(rs.getInt("hot"));
				msbvo.setShot(rs.getInt("shot"));
				msbvo.setJavachip(rs.getInt("javachip"));
				msbvo.setCaramelsyrup(rs.getInt("caramelsyrup"));
				msbvo.setChocosyrup(rs.getInt("chocosyrup"));
				msbvo.setWhippingcream(rs.getInt("whippingcream"));
				msbvo.setPrice(rs.getInt("price"));
				msbvo.setRegdate(rs.getDate("regdate"));
				msbvo.setLike_cnt(rs.getInt("like_cnt"));
				msbvo.setSharing(rs.getInt("SHARING"));
				msbvo.setImg(rs.getString("img"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return msbvo;
	}
	
	
	public ArrayList<OrderVO> newOrder() {
		ArrayList<OrderVO> res = new ArrayList<>();

		try {
			sql = "select * from tb_order "
					+ "where n_order = 0 "
					+ "order by regdate desc";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				OrderVO svo = new OrderVO();

				svo.setSeq(rs.getInt("seq"));
				svo.setUser_id(rs.getString("user_id"));
				svo.setName(rs.getString("name"));
				svo.setEa(rs.getInt("ea"));
				svo.setPrice(rs.getInt("price"));
				svo.setOrder(rs.getInt("n_order"));
				svo.setRegDate(rs.getDate("regdate"));

				res.add(svo);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	
	
	// insert
	public void m_insert(MemberVO vo) {
		System.out.println("insert!!!!!");
		try {
			sql = "insert into member VALUES('"
					+ vo.getName() + "','"
					+ vo.getId() + "','"
					+ vo.getPass() + "','"
					+ vo.getGender() + "','"
					+ vo.getPhone() + "','"
					+ sdf.format(vo.getBrithDate()) + "',"
					+ "sysdate, "
					+ "0, "
					+ "0,'"
					+ vo.getPasshint() + "','"
					+ vo.getHintanswer() + "',"
					+ 0 + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void e_insert(EmployeeVO vo) {
		System.out.println("insert!!!!!");
		try {
			sql = "insert into Employee VALUES('"
					+ vo.getId() + "','"
					+ vo.getE_name() + "','"
					+ vo.getE_pass()
					+ "','직원',"
					+ vo.getPay()
					+ ", '몰랑', '몰랑',"
					+ "sysdate)";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void sb_insert(MySbVO sbvo) {

		try {

			sql = "insert into mystandby VALUES( "
					+ "mystandby_seq.nextval,'" 
					+ sbvo.getTitle() + "','"
					+ sbvo.getUser_id() + "',"
					+ sbvo.getIce() + "," 
					+ sbvo.getHot() + ","
					+ sbvo.getShot() + ","
					+ sbvo.getJavachip() + ","
					+ sbvo.getCaramelsyrup() + ","
					+ sbvo.getChocosyrup() + ","
					+ sbvo.getWhippingcream() + ","
					+ sbvo.getPrice() + ","
					+ "sysdate,"	
					+ sbvo.getLike_cnt() + ",'"
					+ sbvo.getName() + "',"
					+ sbvo.getSharing() + ",'"
					+ sbvo.getImg() + "')";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void o_insert(OrderVO ovo) {

		try {

			sql = "insert into tb_order VALUES("
					+ "order_seq.nextval,'" 
					+ ovo.getUser_id() + "','"
					+ ovo.getName() + "'," 
					+ ovo.getEa() + ","
					+ ovo.getPrice() + ","
					+ ovo.getOrder() + ",'"
					+ sdf.format(new Date()) + " "
					+ sdf2.format(ovo.getRegDate()) + "',"
					+ "(select cate from coffee where name = '"
					+ ovo.getName()+"'), "
					+ "(select cate2 from coffee where name = '"
					+ ovo.getName()+"'))";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void c_insert(CoffeeVO vo) {

		try {
			sql = "insert into coffee VALUES( "
					+ "coffee_seq.nextval, '" 
					+ vo.getCate() + "','"
					+ vo.getGroup() + "','"
					+ vo.getC_name() + "'," 
					+ vo.getPrice() + ",'"
					+ vo.getImg() + "','"
					+ vo.getSort() + "')";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void stock_insert(StockVO vo) {
		try {
			sql = "insert into stock values("
					+ "(select seq "
					+ "from coffee "
					+ "where name = '"+vo.getItem()+"'),'"+
					vo.getItem()+"',"+
					vo.getIn()+","+
					vo.getOut()+",'"+
					vo.getStat()+"','"+
					sdf.format(vo.getRegDate())+"')";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void m_update(MemberVO vo) {

		try {

			sql = "update Member set "
					+ "pass = '" + vo.getPass()
					+ "', phone = '" + vo.getPhone()
					+ "', hintanswer = '" + vo.getHintanswer()
					+"' where id = '" + vo.getId()+"'";

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	public void m_pointUpdate(MemberVO vo) {
		
		try {

			sql = "update Member set "
					+ "point = " + vo.getPoint()
					+ " where id = '" + vo.getId() +"'";		

			System.out.println(sql);
			stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void block_update(MemberVO vo){
		
		try {
			sql = "update member set "
					+ "BLOCK = 1 "
					+ "where id = '"+ vo.getId()+"'";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void grade_update(MemberVO vo) {
		
		try {

			sql = "update member set grade = "
								+ vo.getGrade()
					+ "where id = '" + vo.getId() + "'";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void c_update(CoffeeVO vo){
		
		try {

			sql = "update coffee set "
					+ "cate = '" + vo.getCate()
					+ "', cate2 = '"+ vo.getGroup()
					+ "', name = '"+ vo.getC_name()
					+ "', price = "+ vo.getPrice()
					+ ", img = '"+ vo.getImg()
					+ "' where seq = "+ vo.getC_seq();
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	
//	public void orderStat_update(OrderVO svo){
//		
//		try {
//			sql = "update tb_order set stat = 1 "
//					+ "where seq = "+svo.getSeq();
//			
//			System.out.println(sql);
//			stmt.executeUpdate(sql);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			close();
//		}
//	}
	
	public void order_update(OrderVO svo, int flag){
		try {
			sql = "update tb_order set N_ORDER = "+flag
					+ "where seq = "+svo.getSeq();
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void e_update(EmployeeVO evo) {

		try {

			sql = "update Employee set pass = '" + evo.getE_pass()
			+"', pay = " + evo.getPay()
			+" where id = '" + evo.getId()+"'";

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void share_update(MySbVO sbvo) {
		try {
			sql = "update mystandby set "
					+ "sharing = " + sbvo.getSharing()
					+ " where title = '"
					+ sbvo.getTitle()+ "'";

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void like_update(MySbVO sbvo){
		
		try {

			sql = "update mystandby set "
					+ "like_cnt = " + sbvo.getLike_cnt()
					+ " where title = '" + sbvo.getTitle() + "'";			
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void order_delete(OrderVO vo) {

		try {
			sql = "delete from tb_order where seq = " + vo.getSeq();

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void m_delete(MemberVO vo) {

		try {

			sql = "delete from Member where id = '" + vo.getId()+"'";

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void c_delete(CoffeeVO vo){
		
		try {

			sql = "delete from coffee where seq = "+vo.getC_seq();
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void e_delete(EmployeeVO vo){
		try {

			sql = "delete from Employee where id = '"+vo.getId()+"'";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void sb_delete(MySbVO vo) {
		try {
			sql = "delete from mystandby where title = '" + vo.getTitle()+"'";

			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void close() {

		if (rs != null)		try {rs.close();} catch (Exception e) {}
		if (stmt != null)	try {stmt.close();} catch (Exception e) {}
		if (con != null)	try {con.close();} catch (Exception e) {}
	}

}
