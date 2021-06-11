use QLCTA 
go

-- Tạo bảng
create table LichSuChinhSuaNguyenLieu
(
	thoiGian datetime,
	maNL varchar(10),
	tenNL varchar(200),
	maDV varchar(10) foreign key references DonViTinh(maDV),
	gia money,
	nguoiThucHien nvarchar(100),
	ghiChu nvarchar(50),
	primary key (thoiGian, maNL, tenNL, maDV, gia)
)
go

--drop table LichSuChinhSuaNguyenLieu

-- Tạo trigger cho bảng nguyên liệu
create trigger UTG_UpdateNguyenLieu on NguyenLieu
for update
as 
begin
	insert into LichSuChinhSuaNguyenLieu(thoiGian, maNL, tenNL, maDV, gia, ghiChu)
	select getdate(), deleted.maNL, deleted.tenNL, deleted.maDV, deleted.gia, N'Trước khi chỉnh sửa'
	from deleted

	insert into LichSuChinhSuaNguyenLieu(thoiGian, maNL, tenNL, maDV, gia, ghiChu)
	select getdate(), inserted.maNL, inserted.tenNL, inserted.maDV, inserted.gia, N'Sau khi chỉnh sửa'
	from inserted
end
	
--drop trigger UTG_UpdateNguyenLieu
	
/*
-- Function
create function FU_layDonViTinh(@maDV varchar(10))
returns nvarchar(50)
as 
begin
	declare @tenDV nvarchar(50)

	select @tenDV = tenDV
	from DonViTinh
	where maDV = @maDV

	return @tenDV
end

select dbo.FU_layDonViTinh(maDV)
from LichSuChinhSuaNguyenLieu

drop function FU_layDonViTinh
*/