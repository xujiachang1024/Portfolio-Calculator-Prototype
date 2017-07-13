package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import financial.FixedIncome;
import financial.InstrumentFinancial;
import financial.Portfolio;
import financial.PrivateFund;
import financial.PublicFund;
import utilities.GraphicConstants;
import utilities.GraphicSettings;
import utilities.StringConstants;
import utilities.TextFieldFocusListener;

public class CalculatorGUI extends JFrame{
	
	private Portfolio portfolio;
	// northPanel
	private JTextField mTotalFundsTextField;
	private JButton mTotalFundsButton;
	// centerPanel
	private JLabel mTotalFundsLabel;
	private JLabel mTotalPositionLabel;
	private JLabel mTotalFeesLabel;
	private JLabel mTotalCashLabel;
	private JLabel mTotalEARLabel;
	private JLabel mTotalEarningLabel;
	private DefaultTableModel mAssetModel;
	private JTable mAssetTable;
	private JScrollPane mScrollPane;
	private List<JButton> mRemoveButtons;
	// southPanel
	private JLabel mAlertLabel;
	private JTextField mCodeTextField;
	private JTextField mNameTextField;
	private JTextField mEARTextField;
	private JTextField mTotalTextField;
	private JButton mAddPrivateFundButton;
	private JButton mAddPublicFundButton;
	private JButton mAddFixedIncomeButton;
	private JTextField mRemoveTextField;
	private JButton mRemoveButton;
	private JButton mRemoveAllButton;

	public CalculatorGUI() {
		super("华泰证券投资组合收益计算器");
		this.initializeVariables();
		this.createGUI();
		this.addListeners();
	}
	
	private void initializeVariables() {
		this.portfolio = new Portfolio(0.00);
		// northPanel
		this.mTotalFundsTextField = new JTextField();
		this.mTotalFundsButton = new JButton("设置投资资金规模(万元)");
		// centerPanel
		this.mTotalFundsLabel = new JLabel();
		this.mTotalPositionLabel = new JLabel();
		this.mTotalFeesLabel = new JLabel();
		this.mTotalCashLabel = new JLabel();
		this.mTotalEARLabel = new JLabel();
		this.mTotalEarningLabel = new JLabel();
		this.mAssetModel = new DefaultTableModel() {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       return false;
			   }
		};
		this.mAssetModel.addColumn("列表编号");
		this.mAssetModel.addColumn("产品代码");
		this.mAssetModel.addColumn("产品名称");
		this.mAssetModel.addColumn("产品类别");
		this.mAssetModel.addColumn("产品年化收益率");
		this.mAssetModel.addColumn("产品实际投入(含管理费)");
		this.mAssetModel.addColumn("产品预期回报");
		this.mAssetTable = new JTable(mAssetModel);
		this.mScrollPane = new JScrollPane(mAssetTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.mRemoveButtons = new ArrayList<JButton>();
		this.updateDisplay();
		// southPanel
		this.mAlertLabel = new JLabel("欢迎使用");
		this.mCodeTextField = new JTextField();
		this.mNameTextField = new JTextField();
		this.mEARTextField = new JTextField();
		this.mTotalTextField = new JTextField();
		this.mAddPrivateFundButton = new JButton("添加私募类产品");
		this.mAddPublicFundButton = new JButton("添加公募类产品");
		this.mAddFixedIncomeButton = new JButton("添加固收类产品");
		this.mRemoveTextField = new JTextField();
		this.mRemoveButton = new JButton("移除此产品");
		this.mRemoveAllButton = new JButton("一键清空");
	}
	
	private void updateDisplay() {
		this.mTotalFundsLabel.setText(
				StringConstants.TotalFunds +
				Math.round(portfolio.getTotalFunds() * 100.00) / 100.00 +
				StringConstants.WanYuan + " | ");
		this.mTotalPositionLabel.setText(
				StringConstants.TotalPosition +
				Math.round(portfolio.getTotalPosition() * 100.00) / 100.00 +
				StringConstants.WanYuan + " | ");
		this.mTotalFeesLabel.setText(
				StringConstants.TotalFees +
				Math.round(portfolio.getTotalFees() * 100.00) / 100.00 +
				StringConstants.WanYuan + " | ");
		this.mTotalCashLabel.setText(
				StringConstants.TotalCash +
				Math.round(portfolio.getTotalCash() * 100.00) / 100.00 +
				StringConstants.WanYuan + " | ");
		this.mTotalEARLabel.setText(
				StringConstants.TotalEAR +
				Math.round(portfolio.getTotalEAR() * 100.00) / 100.00 +
				StringConstants.BaiFenShu);
		for (int i = 0; i < mAssetModel.getRowCount(); i++) {
			mAssetModel.setValueAt(Integer.toString(i + 1), i, 0);
		}
	}
	
	private void createGUI() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(1000, 600);
		this.setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));
		this.add(this.createNorthPanel(), BorderLayout.NORTH);
		this.add(this.createCenterPanel(), BorderLayout.CENTER);
		this.add(this.createSouthPanel(), BorderLayout.SOUTH);
		this.setVisible(true);
		this.toFront();
	}
	
	private JPanel createNorthPanel() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JLabel companyLabel = new JLabel("华泰证券无锡分公司");
		JLabel calculatorLabel = new JLabel("投资组合预期年化收益率计算器");
		JPanel northSouthPanel = new JPanel();
		GraphicSettings.setFont(GraphicConstants.fontLarge, companyLabel);
		GraphicSettings.setFont(GraphicConstants.fontMedium, calculatorLabel);
		GraphicSettings.setSize(240, 35, mTotalFundsTextField);
		GraphicSettings.setSize(160, 35, mTotalFundsButton);
		GraphicSettings.setTextAlignment(companyLabel, calculatorLabel);
		northSouthPanel.add(mTotalFundsTextField);
		northSouthPanel.add(mTotalFundsButton);
		northPanel.add(companyLabel, BorderLayout.NORTH);
		northPanel.add(calculatorLabel, BorderLayout.CENTER);
		northPanel.add(northSouthPanel, BorderLayout.SOUTH);
		return northPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel centerNorthPanel = new JPanel();
		centerNorthPanel.add(mTotalFundsLabel);
		centerNorthPanel.add(mTotalPositionLabel);
		centerNorthPanel.add(mTotalFeesLabel);
		centerNorthPanel.add(mTotalCashLabel);
		centerNorthPanel.add(mTotalEARLabel);
		centerPanel.add(centerNorthPanel, BorderLayout.NORTH);
		centerPanel.add(mScrollPane, BorderLayout.CENTER);
		centerPanel.add(mAlertLabel, BorderLayout.SOUTH);
		return centerPanel;
	}
	
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel southNorthPanel = new JPanel();
		JPanel southCenterPanel = new JPanel();
		JPanel southSouthPanel = new JPanel();
		GraphicSettings.setFont(GraphicConstants.fontMedium, mAlertLabel);
		GraphicSettings.setSize(240, 35, mCodeTextField, mNameTextField, mEARTextField, mTotalTextField, mRemoveTextField);
		GraphicSettings.setSize(120, 35, mAddPrivateFundButton, mAddPublicFundButton, mAddFixedIncomeButton, mRemoveButton, mRemoveAllButton);
		GraphicSettings.setTextAlignment(mAlertLabel);
		southNorthPanel.add(mCodeTextField);
		southNorthPanel.add(mNameTextField);
		southNorthPanel.add(mEARTextField);
		southNorthPanel.add(mTotalTextField);
		southCenterPanel.add(mAddPrivateFundButton);
		southCenterPanel.add(mAddPublicFundButton);
		southCenterPanel.add(mAddFixedIncomeButton);
		southSouthPanel.add(mRemoveTextField);
		southSouthPanel.add(mRemoveButton);
		southSouthPanel.add(mRemoveAllButton);
		southPanel.add(southNorthPanel, BorderLayout.NORTH);
		southPanel.add(southCenterPanel, BorderLayout.CENTER);
		southPanel.add(southSouthPanel, BorderLayout.SOUTH);
		return southPanel;
	}
	
	private void addListeners() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mTotalFundsTextField.addFocusListener(
			new TextFieldFocusListener("请输入投资资金规模", this.mTotalFundsTextField));
		
		this.mCodeTextField.addFocusListener(
				new TextFieldFocusListener("请输入产品代码", this.mCodeTextField));
		
		this.mNameTextField.addFocusListener(
				new TextFieldFocusListener("请输入产品名称", this.mNameTextField));
		
		this.mEARTextField.addFocusListener(
				new TextFieldFocusListener("请输入产品年化收益率(百分数)", this.mEARTextField));
		
		this.mTotalTextField.addFocusListener(
				new TextFieldFocusListener("请输入产品投入金额(万元)", this.mTotalTextField));
		
		this.mRemoveTextField.addFocusListener(
				new TextFieldFocusListener("请输入需移除产品的列表编号", this.mRemoveTextField));
		
		this.mTotalFundsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double inTotalFunds = Double.parseDouble(mTotalFundsTextField.getText());
					portfolio.setTotalFunds(inTotalFunds);
					updateDisplay();
					GraphicSettings.setForeground(GraphicConstants.greenText, mAlertLabel);
					mAlertLabel.setText(StringConstants.TotalFundsChangedAlert);
				} catch (Exception exc) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText(StringConstants.InvalidTotalFundsAlert);
				}
			}
			
		});
		
		this.mAddPrivateFundButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inCode = mCodeTextField.getText();
				if (inCode.equals("请输入产品代码")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("添加失败：请输入产品代码");
					return;
				}
				String inName = mNameTextField.getText();
				if (inName.equals("请输入产品名称")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("添加失败：请输入产品名称");
					return;
				}
				try {
					double inEAR = Double.parseDouble(mEARTextField.getText()) / 100.00;
					try {
						double inTotal = Double.parseDouble(mTotalTextField.getText());
						if (inTotal < 100) {
							GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
							mAlertLabel.setText(StringConstants.InvalidPrivateFundPositionAlert);
						} else {
							InstrumentFinancial newAsset = new PrivateFund(inCode, inName, inEAR, inTotal);
							mAssetModel.addRow(new Object[] {
									Integer.toString(mAssetModel.getRowCount() + 1), 
									newAsset.getCode(), newAsset.getName(), "私募基金类", 
									Double.toString(Math.round(newAsset.getEAR() * 10000.00) / 100.00) + StringConstants.BaiFenShu, 
									Double.toString(Math.round(newAsset.getTotal() * 100.00) / 100.00) + StringConstants.WanYuan,
									Double.toString(Math.round(newAsset.getEarning() * 100.00) / 100.00) + StringConstants.WanYuan
							});
							portfolio.add(newAsset);
							updateDisplay();
							GraphicSettings.setForeground(GraphicConstants.greenText, mAlertLabel);
							mAlertLabel.setText(StringConstants.PrivateFundAddedAlert);
						}
					} catch (Exception exc) {
						GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
						mAlertLabel.setText(StringConstants.InvalidTotalReadingAlert);
					}
				} catch (Exception exc) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText(StringConstants.InvalidEARReadingAlert);
				}
			}
			
		});
		
		this.mAddPublicFundButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inCode = mCodeTextField.getText();
				if (inCode.equals("请输入产品代码")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("请输入产品代码");
					return;
				}
				String inName = mNameTextField.getText();
				if (inName.equals("请输入产品名称")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("请输入产品名称");
					return;
				}
				try {
					double inEAR = Double.parseDouble(mEARTextField.getText()) / 100.00;
					try {
						double inTotal = Double.parseDouble(mTotalTextField.getText());
						InstrumentFinancial newAsset = new PublicFund(inCode, inName, inEAR, inTotal);
						mAssetModel.addRow(new String[] {
								Integer.toString(mAssetModel.getRowCount() + 1), 
								newAsset.getCode(), newAsset.getName(), "公募基金类", 
								Double.toString(Math.round(newAsset.getEAR() * 10000.00) / 100.00) + StringConstants.BaiFenShu, 
								Double.toString(Math.round(newAsset.getTotal() * 100.00) / 100.00) + StringConstants.WanYuan,
								Double.toString(Math.round(newAsset.getEarning() * 100.00) / 100.00) + StringConstants.WanYuan
						});
						portfolio.add(newAsset);
						updateDisplay();
						GraphicSettings.setForeground(GraphicConstants.greenText, mAlertLabel);
						mAlertLabel.setText(StringConstants.PublicFundAddedAlert);
					} catch (Exception exc) {
						GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
						mAlertLabel.setText(StringConstants.InvalidTotalReadingAlert);
					}
				} catch (Exception exc) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText(StringConstants.InvalidEARReadingAlert);
				}
			}
			
		});
		
		this.mAddFixedIncomeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inCode = mCodeTextField.getText();
				if (inCode.equals("请输入产品代码")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("请输入产品代码");
					return;
				}
				String inName = mNameTextField.getText();
				if (inName.equals("请输入产品名称")) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText("请输入产品名称");
					return;
				}
				try {
					double inEAR = Double.parseDouble(mEARTextField.getText()) / 100.00;
					try {
						double inTotal = Double.parseDouble(mTotalTextField.getText());
						InstrumentFinancial newAsset = new FixedIncome(inCode, inName, inEAR, inTotal);
						mAssetModel.addRow(new String[] {
								Integer.toString(mAssetModel.getRowCount() + 1), 
								newAsset.getCode(), newAsset.getName(), "固定收益类", 
								Double.toString(Math.round(newAsset.getEAR() * 10000.00) / 100.00) + StringConstants.BaiFenShu, 
								Double.toString(Math.round(newAsset.getTotal() * 100.00) / 100.00) + StringConstants.WanYuan,
								Double.toString(Math.round(newAsset.getEarning() * 100.00) / 100.00) + StringConstants.WanYuan
						});
						portfolio.add(newAsset);
						updateDisplay();
						GraphicSettings.setForeground(GraphicConstants.greenText, mAlertLabel);
						mAlertLabel.setText(StringConstants.FixedIncomeAddedAlert);
					} catch (Exception exc) {
						GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
						mAlertLabel.setText(StringConstants.InvalidTotalReadingAlert);
					}
				} catch (Exception exc) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText(StringConstants.InvalidEARReadingAlert);
				}
			}
			
		});
		
		this.mRemoveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int row = Integer.parseInt(mRemoveTextField.getText());
					if (row < 1 || row > mAssetModel.getRowCount()) {
						GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
						mAlertLabel.setText(StringConstants.InvalidRemoveIndexAlert);
					}
					else {
						portfolio.remove(row - 1);
						mAssetModel.removeRow(row - 1);
						updateDisplay();
					}
				} catch (Exception exc) {
					GraphicSettings.setForeground(GraphicConstants.redText, mAlertLabel);
					mAlertLabel.setText(StringConstants.InvalidRemoveReadingAlert);
				}
			}
			
		});;
		
		this.mRemoveAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				while (mAssetModel.getRowCount() > 0) {
					mAssetModel.removeRow(0);
				}
				portfolio.removeAll();
				updateDisplay();
				GraphicSettings.setForeground(GraphicConstants.greenText, mAlertLabel);
				mAlertLabel.setText(StringConstants.PortfolioRemoveAllAlert);
			}
			
		});
	}

}