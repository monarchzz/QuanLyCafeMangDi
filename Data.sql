USE [master]
GO
/****** Object:  Database [QLCTA]    Script Date: 6/13/2021 1:56:50 AM ******/
CREATE DATABASE [QLCTA]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QLCTA', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\QLCTA.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QLCTA_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\QLCTA_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [QLCTA] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLCTA].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLCTA] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLCTA] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLCTA] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLCTA] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLCTA] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLCTA] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLCTA] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLCTA] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLCTA] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLCTA] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLCTA] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLCTA] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLCTA] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLCTA] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLCTA] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QLCTA] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLCTA] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLCTA] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLCTA] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLCTA] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLCTA] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLCTA] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLCTA] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QLCTA] SET  MULTI_USER 
GO
ALTER DATABASE [QLCTA] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLCTA] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLCTA] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLCTA] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QLCTA] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QLCTA] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [QLCTA] SET QUERY_STORE = OFF
GO
USE [QLCTA]
GO
/****** Object:  Table [dbo].[BanHang]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BanHang](
	[maBH] [varchar](10) NOT NULL,
	[tenTK] [varchar](50) NOT NULL,
	[maCLV] [varchar](10) NOT NULL,
	[tg] [datetime] NOT NULL,
	[tongTien] [money] NOT NULL,
 CONSTRAINT [PK_BanHang] PRIMARY KEY CLUSTERED 
(
	[maBH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CaLamViec]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CaLamViec](
	[maCLV] [varchar](10) NOT NULL,
	[maDD] [varchar](10) NOT NULL,
	[caLamViec] [nvarchar](20) NOT NULL,
	[ngay] [date] NOT NULL,
	[TK1] [varchar](50) NOT NULL,
	[TK2] [varchar](50) NULL,
 CONSTRAINT [PK_CaLamViec_1] PRIMARY KEY CLUSTERED 
(
	[maCLV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChamLuong]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChamLuong](
	[maLuong] [varchar](10) NOT NULL,
	[tenTK] [varchar](50) NOT NULL,
	[thangNam] [varchar](10) NOT NULL,
	[luongCoBan] [int] NOT NULL,
	[soCaLam] [int] NOT NULL,
	[heSoLuong] [float] NOT NULL,
	[thanhTien] [money] NOT NULL,
	[trangThai] [tinyint] NOT NULL,
 CONSTRAINT [PK_ChamLuong_1] PRIMARY KEY CLUSTERED 
(
	[maLuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietBH]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietBH](
	[maBH] [varchar](10) NOT NULL,
	[maSP] [varchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietBH_1] PRIMARY KEY CLUSTERED 
(
	[maBH] ASC,
	[maSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietCT]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietCT](
	[maCT] [varchar](10) NOT NULL,
	[maNL] [varchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietCT] PRIMARY KEY CLUSTERED 
(
	[maCT] ASC,
	[maNL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietDK]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietDK](
	[maDK] [varchar](10) NOT NULL,
	[maNL] [varchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietDK] PRIMARY KEY CLUSTERED 
(
	[maDK] ASC,
	[maNL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietNhapXuat]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietNhapXuat](
	[maNX] [varchar](10) NOT NULL,
	[maNL] [varchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietNhapXuat_1] PRIMARY KEY CLUSTERED 
(
	[maNX] ASC,
	[maNL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CongThuc]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CongThuc](
	[maCT] [varchar](10) NOT NULL,
	[maSP] [varchar](10) NOT NULL,
	[cachLam] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_CongThuc_1] PRIMARY KEY CLUSTERED 
(
	[maCT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DangKi]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DangKi](
	[maDK] [varchar](10) NOT NULL,
	[maCLV] [varchar](10) NOT NULL,
	[tg] [datetime] NOT NULL,
	[ghiChu] [nvarchar](max) NULL,
 CONSTRAINT [PK_DangKi] PRIMARY KEY CLUSTERED 
(
	[maDK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DiaDiem]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DiaDiem](
	[maDD] [varchar](10) NOT NULL,
	[viTri] [nvarchar](max) NOT NULL,
	[trangThai] [tinyint] NOT NULL,
 CONSTRAINT [PK_DiaDiem] PRIMARY KEY CLUSTERED 
(
	[maDD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonViTinh]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonViTinh](
	[maDV] [varchar](10) NOT NULL,
	[tenDV] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_DonVi] PRIMARY KEY CLUSTERED 
(
	[maDV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LichSuChinhSuaNguyenLieu]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LichSuChinhSuaNguyenLieu](
	[thoiGian] [datetime] NOT NULL,
	[maNL] [varchar](10) NOT NULL,
	[tenNL] [nvarchar](200) NOT NULL,
	[maDV] [varchar](10) NOT NULL,
	[gia] [money] NOT NULL,
	[soLuong] [int] NOT NULL,
	[nguoiThucHien] [nvarchar](100) NULL,
	[ghiChu] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[thoiGian] ASC,
	[maNL] ASC,
	[tenNL] ASC,
	[maDV] ASC,
	[gia] ASC,
	[soLuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NguyenLieu]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NguyenLieu](
	[maNL] [varchar](10) NOT NULL,
	[tenNL] [nvarchar](200) NOT NULL,
	[maDV] [varchar](10) NOT NULL,
	[gia] [money] NOT NULL,
	[soLuong] [int] NOT NULL,
 CONSTRAINT [PK_NguyenLieu] PRIMARY KEY CLUSTERED 
(
	[maNL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[tenTK] [varchar](50) NOT NULL,
	[matKhau] [varchar](200) NOT NULL,
	[chucVu] [nvarchar](200) NOT NULL,
	[cmnd] [varchar](20) NOT NULL,
	[sdt] [varchar](12) NOT NULL,
	[gioiTinh] [nvarchar](10) NOT NULL,
	[tenNV] [nvarchar](200) NOT NULL,
	[trangThai] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK__NhanVien__FB731BDF7315B2A4] PRIMARY KEY CLUSTERED 
(
	[tenTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhapXuat]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhapXuat](
	[maNX] [varchar](10) NOT NULL,
	[tenTK] [varchar](50) NOT NULL,
	[tg] [datetime] NOT NULL,
	[trangThai] [nvarchar](20) NOT NULL,
	[thanhTien] [money] NULL,
	[ghiChu] [nvarchar](max) NULL,
 CONSTRAINT [PK_NhapXuat_1] PRIMARY KEY CLUSTERED 
(
	[maNX] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SanPham]    Script Date: 6/13/2021 1:56:50 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SanPham](
	[maSP] [varchar](10) NOT NULL,
	[tenSP] [nvarchar](200) NOT NULL,
	[maDV] [varchar](10) NOT NULL,
	[gia] [money] NOT NULL,
 CONSTRAINT [PK_SanPham] PRIMARY KEY CLUSTERED 
(
	[maSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[BanHang] ([maBH], [tenTK], [maCLV], [tg], [tongTien]) VALUES (N'BH1', N'nv03', N'CL6', CAST(N'2021-06-12T23:38:51.000' AS DateTime), 52000.0000)
INSERT [dbo].[BanHang] ([maBH], [tenTK], [maCLV], [tg], [tongTien]) VALUES (N'BH2', N'nv03', N'CL6', CAST(N'2021-06-12T23:40:06.000' AS DateTime), 66000.0000)
GO
INSERT [dbo].[CaLamViec] ([maCLV], [maDD], [caLamViec], [ngay], [TK1], [TK2]) VALUES (N'CL1', N'DD2', N'Sáng', CAST(N'2021-06-12' AS Date), N'nv01', NULL)
INSERT [dbo].[CaLamViec] ([maCLV], [maDD], [caLamViec], [ngay], [TK1], [TK2]) VALUES (N'CL2', N'DD5', N'Sáng', CAST(N'2021-06-15' AS Date), N'nv01', N'nv02')
INSERT [dbo].[CaLamViec] ([maCLV], [maDD], [caLamViec], [ngay], [TK1], [TK2]) VALUES (N'CL3', N'DD1', N'Chiều', CAST(N'2021-06-23' AS Date), N'nv03', NULL)
INSERT [dbo].[CaLamViec] ([maCLV], [maDD], [caLamViec], [ngay], [TK1], [TK2]) VALUES (N'CL4', N'DD4', N'Chiều', CAST(N'2021-06-14' AS Date), N'nv03', N'nv02')
INSERT [dbo].[CaLamViec] ([maCLV], [maDD], [caLamViec], [ngay], [TK1], [TK2]) VALUES (N'CL6', N'DD1', N'Chiều', CAST(N'2021-06-12' AS Date), N'nv03', NULL)
GO
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH1', N'SP0', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH1', N'SP1', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH1', N'SP3', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH1', N'SP4', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH2', N'SP0', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH2', N'SP1', 1)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH2', N'SP3', 2)
INSERT [dbo].[ChiTietBH] ([maBH], [maSP], [soLuong]) VALUES (N'BH2', N'SP4', 1)
GO
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT0', N'NL0', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT0', N'NL1', 1)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT0', N'NL2', 3)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT1', N'NL0', 3)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT1', N'NL4', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT2', N'NL0', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT2', N'NL1', 1)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT2', N'NL2', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT3', N'NL0', 1)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT3', N'NL3', 3)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT3', N'NL4', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT4', N'NL0', 2)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT4', N'NL4', 1)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT5', N'NL0', 1)
INSERT [dbo].[ChiTietCT] ([maCT], [maNL], [soLuong]) VALUES (N'CT5', N'NL3', 1)
GO
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK1', N'NL0', 25)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK1', N'NL1', 5)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK1', N'NL2', 15)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK1', N'NL4', 10)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK2', N'NL0', 8)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK2', N'NL1', 1)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK2', N'NL2', 3)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK2', N'NL3', 3)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK2', N'NL4', 5)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK3', N'NL0', 13)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK3', N'NL1', 2)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK3', N'NL2', 6)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK3', N'NL3', 9)
INSERT [dbo].[ChiTietDK] ([maDK], [maNL], [soLuong]) VALUES (N'DK3', N'NL4', 10)
GO
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N0', N'NL0', 50)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N1', N'NL1', 40)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N10', N'NL1', 6)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N11', N'NL2', 9)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N2', N'NL2', 50)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N3', N'NL3', 45)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N4', N'NL4', 30)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N5', N'NL0', 50)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N5', N'NL1', 25)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N5', N'NL2', 33)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N5', N'NL3', 44)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N5', N'NL4', 22)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N6', N'NL0', 32)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N6', N'NL1', 42)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N6', N'NL2', 5)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N6', N'NL3', 2)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N7', N'NL1', 52)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N8', N'NL0', 52)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N8', N'NL1', 52)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N8', N'NL2', 24)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N8', N'NL3', 23)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N8', N'NL4', 42)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'N9', N'NL1', 6)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T1', N'NL0', 0)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T1', N'NL1', 0)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T1', N'NL2', 0)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T1', N'NL3', 0)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T1', N'NL4', 0)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T2', N'NL0', 4)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T2', N'NL1', 1)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T2', N'NL2', 3)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T2', N'NL3', 3)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'T2', N'NL4', 3)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X1', N'NL0', 25)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X1', N'NL1', 5)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X1', N'NL2', 15)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X1', N'NL4', 10)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X2', N'NL0', 8)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X2', N'NL1', 1)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X2', N'NL2', 3)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X2', N'NL3', 3)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X2', N'NL4', 5)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X3', N'NL0', 13)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X3', N'NL1', 2)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X3', N'NL2', 6)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X3', N'NL3', 9)
INSERT [dbo].[ChiTietNhapXuat] ([maNX], [maNL], [soLuong]) VALUES (N'X3', N'NL4', 10)
GO
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT0', N'SP0', N'Bước 1: Cho 2 – 3 thìa cafe bột vào phin, bạn nên cho nhiều một chút để đảm bảo độ đặc cho ly cafe nâu sữa. Sau khi cho vào phin, bạn lắc nhẹ để dàn đều cafe, giúp mặt cà phê phẳng thì sẽ dễ nén hơn.
Bước 2: Đổ sữa đặc vào ly với lượng phù hợp với sở thích của bạn. Nếu muốn uống ngọt, bạn cho nhiều sữa một chút. Nhiều bạn lại thích uống cafe vị đắng nhiều hơn thì bạn có thể bớt sữa trong cách pha cafe sữa đá ngon.
Bước 3: Đậy nắp nén cà phê lại. Nếu phần này có chốt nén thì bạn vặn chặt để nén cà phê xuống (có thể dùng mũi dao hoặc chuôi thìa). Trước khi pha cà phê sữa đá ngon, bạn cho một thìa nước nóng vào trước để làm ẩm và giúp cà phê nở ra. Đây là bí quyết trong cách pha cafe sữa đá mà bạn cần nhớ để giúp cà phê nở hơn, khi cho nước sôi vào sẽ chạy chậm, cà phê nhỏ giọt đậm đặc hơn đấy.
Bước 4: Đun nước sôi rồi rót từ từ vào phin đựng cà phê cho cà phê ngấm dần. Bạn rót một lượng nước vừa đủ, ngập quá mặt cà phê một chút là được.
Bước 5: Trong quá trình đợi cà phê nhỏ giọt, nếu thấy chảy nhanh, bạn lại vặn chặt chốt nén cà phê hơnNếu cà phê chảy nhanh, bạn nên điều chỉnh lại chốt nén để cách pha cafe sữa đá của bạn đạt được lượng nước vừa đủ dùng nhất.
Bước 6: Cuối cùng ngồi đợi cho cà phê nhỏ giọt hết là bạn đã có ngay cốc cà phê sữa thơm ngon đậm đà rồi. Khi uống chỉ cần cho thêm vài cục đá nhỏ sẽ ngon hơn nhiều đấy.')
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT1', N'SP1', N'Bước 1: Vệ sinh sạch sẽ máy xay. Đổ hỗn hợp và máy và say nhuyễn.

Bước 2: Xịt kem lên trên, và trang trí bằng sauce caramel hoặc sauce chocolate tùy theo sở thích.

Bước 3: Làm kem whipping cream xịt lên trên

Gợi ý nhỏ: Bạn có thể thay syrup Caramel bằng vị các vị Hazelnut, Cinnamon, Vanilla, Iris, Macadamia Nut')
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT2', N'SP2', N'Bước 1: Cho cà phê vào trong phin, cho 10ml nước sôi vào và ủ trong 20 giây. Sau đó cho tiếp 100ml nước sôi vào ủ đến khi hết nước.

Bước 2: Cho phần cà phê vừa pha vào bình shake lắc nhẹ để tạo bọt.

Bước 3: Cho sữa đặc, sữa tươi không đường vào ly, sau đó đổ cà phê đã tạo bọt lên trên.

Bước 4: Cho đá bào vào ly bạc xỉu và thưởng thức.')
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT3', N'SP3', N'Bước 1: Pha cà phê bằng phin với công thức như ở bước 1 của cách pha bạc xỉu đá.

Bước 2: Khi cà phê đã pha vẫn còn nóng, bạn cho vào bình shake và lắc nhẹ để tạo bọt.

Bước 3: Cho sữa tươi cùng sữa đặc và nồi, đun lửa nhỏ và liên tục khuấy đều đến khi sữa sôi lăn tăn là được.

Bước 4: Đổ hỗn hợp sữa ra cốc, sau đó đổ cà phê đã được tạo bọt cà phê lên trên và thưởng thức ngay khi còn nóng.')
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT4', N'SP4', N'Rửa thật sạch tách và phin Coffee, sau đó trụng nước sôi.
Đặt tách ở dưới, phía trên là phin. Cho bột Cafe rang xay vào phin, lắc nhẹ cho Cafe sạch san đều, đặt vỉ lên trên bột cà phê, nén nhẹ.
Rót một chút nước sôi đến đều khắp mặt vỉ. Đợi cho chút nước này ngấm đều làm bột cafe trong phin nở hết. Rót tiếp nước sôi từ từ tới gần đầy phin, đậy nắp phin. Thời điểm lượng Cà phê hạt rang chảy xuống tách hết thì bạn nhấc phin ra.')
INSERT [dbo].[CongThuc] ([maCT], [maSP], [cachLam]) VALUES (N'CT5', N'SP5', N'Bước 1: Pha cà phê bột bằng phin pha hoặc bằng máy pha cà phê. Tỷ lệ 2 – 4 thìa cho một ly cafe 100ml;
Bước 2: Trong thời gian đó, đổ sữa đặc vào ly cafe sạch, định lượng gợi ý là 25ml. Nếu bạn thích uống ngọt thì có thể thêm sữa tùy thích;
Bước 3: Rót nhẹ cafe vào ly sữa đặc, thêm một chiếc thìa nhỏ. Vậy là xong!')
GO
INSERT [dbo].[DangKi] ([maDK], [maCLV], [tg], [ghiChu]) VALUES (N'DK1', N'CL2', CAST(N'2021-06-12T23:32:55.000' AS DateTime), N'')
INSERT [dbo].[DangKi] ([maDK], [maCLV], [tg], [ghiChu]) VALUES (N'DK2', N'CL6', CAST(N'2021-06-12T23:38:42.000' AS DateTime), N'')
INSERT [dbo].[DangKi] ([maDK], [maCLV], [tg], [ghiChu]) VALUES (N'DK3', N'CL6', CAST(N'2021-06-12T23:39:35.000' AS DateTime), N'')
GO
INSERT [dbo].[DiaDiem] ([maDD], [viTri], [trangThai]) VALUES (N'DD1', N'97 Man Thiện, Tăng Nhơn Phú A, Quận 9, Thành phố Hồ Chí Minh', 1)
INSERT [dbo].[DiaDiem] ([maDD], [viTri], [trangThai]) VALUES (N'DD2', N'1 Võ Văn Ngân, Linh Tây, Thủ Đức, Thành phố Hồ Chí Minh', 1)
INSERT [dbo].[DiaDiem] ([maDD], [viTri], [trangThai]) VALUES (N'DD3', N'225 Nguyễn Văn Cừ Phường 4, Quận 5, Thành phố Hồ Chí Minh', 1)
INSERT [dbo].[DiaDiem] ([maDD], [viTri], [trangThai]) VALUES (N'DD4', N'07 Công Trường Lam Sơn, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh', 1)
INSERT [dbo].[DiaDiem] ([maDD], [viTri], [trangThai]) VALUES (N'DD5', N'50 Lê Văn Việt, P. Hiệp Phú, Q. 9, TP. Hồ Chí Minh ', 1)
GO
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'G0', N'Gam')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'G1', N'Gói')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'H0', N'Hộp')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'K0', N'Kg')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'L0', N'Lít')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'L1', N'Lon')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'L2', N'Ly')
INSERT [dbo].[DonViTinh] ([maDV], [tenDV]) VALUES (N'M0', N'ml')
GO
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:08.387' AS DateTime), N'NL1', N'Bột vani', N'G0', 30000.0000, 210, N'admin - Phạm Hoàng Phúc', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:08.387' AS DateTime), N'NL1', N'Bột vani', N'G1', 30000.0000, 210, N'admin - Phạm Hoàng Phúc', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:12.567' AS DateTime), N'NL1', N'Bột vani', N'G0', 30000.0000, 210, N'admin - Phạm Hoàng Phúc', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:12.567' AS DateTime), N'NL1', N'Bột vani', N'G1', 30000.0000, 210, N'admin - Phạm Hoàng Phúc', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:48.310' AS DateTime), N'NL4', N'Đường', N'K0', 30000.0000, 72, N'admin - Phạm Hoàng Phúc', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:48.310' AS DateTime), N'NL4', N'Đường', N'K0', 45000.0000, 72, N'admin - Phạm Hoàng Phúc', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:52.677' AS DateTime), N'NL4', N'Đường', N'K0', 30000.0000, 72, N'admin - Phạm Hoàng Phúc', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:51:52.677' AS DateTime), N'NL4', N'Đường', N'K0', 45000.0000, 72, N'admin - Phạm Hoàng Phúc', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:52:22.087' AS DateTime), N'NL0', N'Café', N'K0', 95000.0000, 142, N'ql01 - Lê Trần Ngọc Vân', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:52:22.087' AS DateTime), N'NL0', N'Café', N'K0', 100000.0000, 142, N'ql01 - Lê Trần Ngọc Vân', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:52:46.047' AS DateTime), N'NL0', N'Café', N'K0', 95000.0000, 142, N'ql01 - Lê Trần Ngọc Vân', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:52:46.047' AS DateTime), N'NL0', N'Café', N'K0', 105000.0000, 142, N'ql01 - Lê Trần Ngọc Vân', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:53:25.740' AS DateTime), N'NL4', N'Đường', N'K0', 30000.0000, 72, N'ql01 - Lê Trần Ngọc Vân', N'Trước khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:53:25.740' AS DateTime), N'NL4', N'Đường 1', N'K0', 30000.0000, 72, N'ql01 - Lê Trần Ngọc Vân', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:53:30.473' AS DateTime), N'NL4', N'Đường', N'K0', 30000.0000, 72, N'ql01 - Lê Trần Ngọc Vân', N'Sau khi chỉnh sửa')
INSERT [dbo].[LichSuChinhSuaNguyenLieu] ([thoiGian], [maNL], [tenNL], [maDV], [gia], [soLuong], [nguoiThucHien], [ghiChu]) VALUES (CAST(N'2021-06-13T01:53:30.473' AS DateTime), N'NL4', N'Đường 1', N'K0', 30000.0000, 72, N'ql01 - Lê Trần Ngọc Vân', N'Trước khi chỉnh sửa')
GO
INSERT [dbo].[NguyenLieu] ([maNL], [tenNL], [maDV], [gia], [soLuong]) VALUES (N'NL0', N'Café', N'K0', 105000.0000, 142)
INSERT [dbo].[NguyenLieu] ([maNL], [tenNL], [maDV], [gia], [soLuong]) VALUES (N'NL1', N'Bột vani', N'G1', 30000.0000, 210)
INSERT [dbo].[NguyenLieu] ([maNL], [tenNL], [maDV], [gia], [soLuong]) VALUES (N'NL2', N'Sữa tươi', N'H0', 50000.0000, 100)
INSERT [dbo].[NguyenLieu] ([maNL], [tenNL], [maDV], [gia], [soLuong]) VALUES (N'NL3', N'Sữa đặc', N'H0', 55000.0000, 105)
INSERT [dbo].[NguyenLieu] ([maNL], [tenNL], [maDV], [gia], [soLuong]) VALUES (N'NL4', N'Đường', N'K0', 30000.0000, 72)
GO
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'admin', N'2fbdce248e29f4c6ec6ac118cdfb1cad0c1f70b5d89878a72d5e231e753d53bb6d622285610f3a757abba7ea9955ba7beca3842d5e89cf7231805b0058b95db1', N'admin', N'232323232', N'0923232323', N'Nam', N'Phạm Hoàng Phúc', N'1')
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'nv01', N'eaa625a6169e426343b3702924c7939ea508b17b9188e271d2d1cc2b54fc01109f50e14a9425088fb7a294bf54c9e33317e60fdc9cb583b99dec124ba60a1ab5', N'nhân viên', N'232524323222', N'2332425252', N'Nam', N'Huỳnh Hoàng Nghiệp', N'1')
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'nv02', N'eaa625a6169e426343b3702924c7939ea508b17b9188e271d2d1cc2b54fc01109f50e14a9425088fb7a294bf54c9e33317e60fdc9cb583b99dec124ba60a1ab5', N'nhân viên', N'235294039', N'0990909099', N'Nữ', N'Lê Văn Quốc Việt', N'1')
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'nv03', N'eaa625a6169e426343b3702924c7939ea508b17b9188e271d2d1cc2b54fc01109f50e14a9425088fb7a294bf54c9e33317e60fdc9cb583b99dec124ba60a1ab5', N'nhân viên', N'923528392868', N'9204849383', N'Nữ', N'Hoàng Thị Thanh Thuỷ', N'1')
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'ql01', N'eaa625a6169e426343b3702924c7939ea508b17b9188e271d2d1cc2b54fc01109f50e14a9425088fb7a294bf54c9e33317e60fdc9cb583b99dec124ba60a1ab5', N'quản lý', N'093029302', N'0938293829', N'Nữ', N'Lê Trần Ngọc Vân', N'1')
INSERT [dbo].[NhanVien] ([tenTK], [matKhau], [chucVu], [cmnd], [sdt], [gioiTinh], [tenNV], [trangThai]) VALUES (N'ql02', N'eaa625a6169e426343b3702924c7939ea508b17b9188e271d2d1cc2b54fc01109f50e14a9425088fb7a294bf54c9e33317e60fdc9cb583b99dec124ba60a1ab5', N'quản lý', N'903233234567', N'9087654234', N'Nam', N'Nguyễn Trung Hiếu', N'1')
GO
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N0', N'admin', CAST(N'2021-06-12T22:52:10.080' AS DateTime), N'0', 5000000.0000, N'Thêm nguyên liệu mới vào kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N1', N'admin', CAST(N'2021-06-12T22:56:19.923' AS DateTime), N'0', 1200000.0000, N'Thêm nguyên liệu mới vào kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N10', N'admin', CAST(N'2021-06-13T01:39:56.977' AS DateTime), N'0', 180000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N11', N'admin', CAST(N'2021-06-13T01:55:05.587' AS DateTime), N'0', 450000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N2', N'admin', CAST(N'2021-06-12T22:56:47.620' AS DateTime), N'0', 2500000.0000, N'Thêm nguyên liệu mới vào kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N3', N'admin', CAST(N'2021-06-12T22:57:05.800' AS DateTime), N'0', 2475000.0000, N'Thêm nguyên liệu mới vào kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N4', N'admin', CAST(N'2021-06-12T22:59:02.417' AS DateTime), N'0', 900000.0000, N'Thêm nguyên liệu mới vào kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N5', N'admin', CAST(N'2021-06-12T23:00:02.780' AS DateTime), N'0', 10480000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N6', N'admin', CAST(N'2021-06-12T23:00:16.563' AS DateTime), N'0', 4820000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N7', N'ql02', CAST(N'2021-06-12T23:06:40.447' AS DateTime), N'0', 1560000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N8', N'ql02', CAST(N'2021-06-12T23:06:52.470' AS DateTime), N'0', 10485000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'N9', N'admin', CAST(N'2021-06-13T01:37:11.637' AS DateTime), N'0', 180000.0000, N'Nhập nguyên liệu về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'T1', N'nv03', CAST(N'2021-06-12T23:39:05.350' AS DateTime), N'2', 0.0000, N'Trả nguyên liệu thừa về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'T2', N'nv03', CAST(N'2021-06-12T23:40:12.667' AS DateTime), N'2', 0.0000, N'Trả nguyên liệu thừa về kho')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'X1', N'nv01', CAST(N'2021-06-12T23:33:00.480' AS DateTime), N'1', 0.0000, N'Đăng kí nguyên liệu')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'X2', N'nv03', CAST(N'2021-06-12T23:38:45.927' AS DateTime), N'1', 0.0000, N'Đăng kí nguyên liệu')
INSERT [dbo].[NhapXuat] ([maNX], [tenTK], [tg], [trangThai], [thanhTien], [ghiChu]) VALUES (N'X3', N'nv03', CAST(N'2021-06-12T23:39:36.727' AS DateTime), N'1', 0.0000, N'Đăng kí nguyên liệu')
GO
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP0', N'Café sữa đá', N'L2', 14000.0000)
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP1', N'Café đen đá', N'L2', 12000.0000)
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP2', N'Bạc xỉu lạnh', N'L2', 14000.0000)
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP3', N'Bạc xỉu nóng', N'L2', 14000.0000)
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP4', N'Cafe đen nóng', N'L2', 12000.0000)
INSERT [dbo].[SanPham] ([maSP], [tenSP], [maDV], [gia]) VALUES (N'SP5', N'Café sữa nóng', N'L2', 14000.0000)
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__NhanVien__22BEB80D5D0334DF]    Script Date: 6/13/2021 1:56:50 AM ******/
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [UQ__NhanVien__22BEB80D5D0334DF] UNIQUE NONCLUSTERED 
(
	[cmnd] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[BanHang]  WITH CHECK ADD  CONSTRAINT [FK_BanHang_CaLamViec] FOREIGN KEY([maCLV])
REFERENCES [dbo].[CaLamViec] ([maCLV])
GO
ALTER TABLE [dbo].[BanHang] CHECK CONSTRAINT [FK_BanHang_CaLamViec]
GO
ALTER TABLE [dbo].[BanHang]  WITH CHECK ADD  CONSTRAINT [FK_BanHang_NhanVien] FOREIGN KEY([tenTK])
REFERENCES [dbo].[NhanVien] ([tenTK])
GO
ALTER TABLE [dbo].[BanHang] CHECK CONSTRAINT [FK_BanHang_NhanVien]
GO
ALTER TABLE [dbo].[CaLamViec]  WITH CHECK ADD  CONSTRAINT [FK_CaLamViec_DiaDiem] FOREIGN KEY([maDD])
REFERENCES [dbo].[DiaDiem] ([maDD])
GO
ALTER TABLE [dbo].[CaLamViec] CHECK CONSTRAINT [FK_CaLamViec_DiaDiem]
GO
ALTER TABLE [dbo].[CaLamViec]  WITH CHECK ADD  CONSTRAINT [FK_CaLamViec_NhanVien] FOREIGN KEY([TK1])
REFERENCES [dbo].[NhanVien] ([tenTK])
GO
ALTER TABLE [dbo].[CaLamViec] CHECK CONSTRAINT [FK_CaLamViec_NhanVien]
GO
ALTER TABLE [dbo].[CaLamViec]  WITH CHECK ADD  CONSTRAINT [FK_CaLamViec_NhanVien1] FOREIGN KEY([TK2])
REFERENCES [dbo].[NhanVien] ([tenTK])
GO
ALTER TABLE [dbo].[CaLamViec] CHECK CONSTRAINT [FK_CaLamViec_NhanVien1]
GO
ALTER TABLE [dbo].[ChamLuong]  WITH CHECK ADD  CONSTRAINT [FK_ChamLuong_NhanVien] FOREIGN KEY([tenTK])
REFERENCES [dbo].[NhanVien] ([tenTK])
GO
ALTER TABLE [dbo].[ChamLuong] CHECK CONSTRAINT [FK_ChamLuong_NhanVien]
GO
ALTER TABLE [dbo].[ChiTietBH]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietBH_BanHang1] FOREIGN KEY([maBH])
REFERENCES [dbo].[BanHang] ([maBH])
GO
ALTER TABLE [dbo].[ChiTietBH] CHECK CONSTRAINT [FK_ChiTietBH_BanHang1]
GO
ALTER TABLE [dbo].[ChiTietBH]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietBH_SanPham1] FOREIGN KEY([maSP])
REFERENCES [dbo].[SanPham] ([maSP])
GO
ALTER TABLE [dbo].[ChiTietBH] CHECK CONSTRAINT [FK_ChiTietBH_SanPham1]
GO
ALTER TABLE [dbo].[ChiTietCT]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietCT_CongThuc1] FOREIGN KEY([maCT])
REFERENCES [dbo].[CongThuc] ([maCT])
GO
ALTER TABLE [dbo].[ChiTietCT] CHECK CONSTRAINT [FK_ChiTietCT_CongThuc1]
GO
ALTER TABLE [dbo].[ChiTietCT]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietCT_NguyenLieu1] FOREIGN KEY([maNL])
REFERENCES [dbo].[NguyenLieu] ([maNL])
GO
ALTER TABLE [dbo].[ChiTietCT] CHECK CONSTRAINT [FK_ChiTietCT_NguyenLieu1]
GO
ALTER TABLE [dbo].[ChiTietDK]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietDK_DangKi] FOREIGN KEY([maDK])
REFERENCES [dbo].[DangKi] ([maDK])
GO
ALTER TABLE [dbo].[ChiTietDK] CHECK CONSTRAINT [FK_ChiTietDK_DangKi]
GO
ALTER TABLE [dbo].[ChiTietDK]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietDK_NguyenLieu] FOREIGN KEY([maNL])
REFERENCES [dbo].[NguyenLieu] ([maNL])
GO
ALTER TABLE [dbo].[ChiTietDK] CHECK CONSTRAINT [FK_ChiTietDK_NguyenLieu]
GO
ALTER TABLE [dbo].[ChiTietNhapXuat]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietNhapXuat_NguyenLieu] FOREIGN KEY([maNL])
REFERENCES [dbo].[NguyenLieu] ([maNL])
GO
ALTER TABLE [dbo].[ChiTietNhapXuat] CHECK CONSTRAINT [FK_ChiTietNhapXuat_NguyenLieu]
GO
ALTER TABLE [dbo].[ChiTietNhapXuat]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietNhapXuat_NhapXuat] FOREIGN KEY([maNX])
REFERENCES [dbo].[NhapXuat] ([maNX])
GO
ALTER TABLE [dbo].[ChiTietNhapXuat] CHECK CONSTRAINT [FK_ChiTietNhapXuat_NhapXuat]
GO
ALTER TABLE [dbo].[CongThuc]  WITH CHECK ADD  CONSTRAINT [FK_CongThuc_SanPham] FOREIGN KEY([maSP])
REFERENCES [dbo].[SanPham] ([maSP])
GO
ALTER TABLE [dbo].[CongThuc] CHECK CONSTRAINT [FK_CongThuc_SanPham]
GO
ALTER TABLE [dbo].[DangKi]  WITH CHECK ADD  CONSTRAINT [FK_DangKi_CaLamViec] FOREIGN KEY([maCLV])
REFERENCES [dbo].[CaLamViec] ([maCLV])
GO
ALTER TABLE [dbo].[DangKi] CHECK CONSTRAINT [FK_DangKi_CaLamViec]
GO
ALTER TABLE [dbo].[LichSuChinhSuaNguyenLieu]  WITH CHECK ADD FOREIGN KEY([maDV])
REFERENCES [dbo].[DonViTinh] ([maDV])
GO
ALTER TABLE [dbo].[NguyenLieu]  WITH CHECK ADD  CONSTRAINT [FK_NguyenLieu_DonVi] FOREIGN KEY([maDV])
REFERENCES [dbo].[DonViTinh] ([maDV])
GO
ALTER TABLE [dbo].[NguyenLieu] CHECK CONSTRAINT [FK_NguyenLieu_DonVi]
GO
ALTER TABLE [dbo].[NhapXuat]  WITH CHECK ADD  CONSTRAINT [FK_NhapXuat_NhanVien] FOREIGN KEY([tenTK])
REFERENCES [dbo].[NhanVien] ([tenTK])
GO
ALTER TABLE [dbo].[NhapXuat] CHECK CONSTRAINT [FK_NhapXuat_NhanVien]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_DonVi] FOREIGN KEY([maDV])
REFERENCES [dbo].[DonViTinh] ([maDV])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_SanPham_DonVi]

GO
create trigger UTG_UpdateNguyenLieu on NguyenLieu
for update
as 
begin
	insert into LichSuChinhSuaNguyenLieu(thoiGian, maNL, tenNL, maDV, gia, soLuong, ghiChu)
	select getdate(), deleted.maNL, deleted.tenNL, deleted.maDV, deleted.gia, deleted.soLuong, N'Trước khi chỉnh sửa'
	from deleted

	insert into LichSuChinhSuaNguyenLieu(thoiGian, maNL, tenNL, maDV, gia, soLuong, ghiChu)
	select getdate(), inserted.maNL, inserted.tenNL, inserted.maDV, inserted.gia, inserted.soLuong, N'Sau khi chỉnh sửa'
	from inserted
end

GO
USE [master]
GO
ALTER DATABASE [QLCTA] SET  READ_WRITE 
GO
