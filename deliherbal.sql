-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 16, 2015 at 06:57 AM
-- Server version: 5.5.43-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `deliherbal`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `kdBrg` char(10) NOT NULL,
  `nmBrg` varchar(40) NOT NULL,
  `kmsn` varchar(6) NOT NULL,
  `hrgBeli` int(8) NOT NULL,
  `hrgJual` int(8) NOT NULL,
  `stok` int(3) NOT NULL,
  PRIMARY KEY (`kdBrg`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kdBrg`, `nmBrg`, `kmsn`, `hrgBeli`, `hrgJual`, `stok`) VALUES
('hdh000001', 'Sari Kurma Sahara', 'Kotak', 18000, 28000, 4),
('hdh000002', 'Madu Batuk Eliman', 'Kotak', 25000, 35000, 7),
('hdh000003', 'Naturatik', 'Kotak', 27000, 45000, 9),
('hdh000004', 'Gensubur', 'Kotak', 35000, 50000, 7),
('hdh000005', 'Dakoles', 'Kotak', 27000, 45000, 7),
('hdh000006', 'Teh Kulit Manggis Gholiban', 'Kotak', 15000, 25000, 1),
('hdh000007', 'Teh Daun Sirsak Gholiban', 'Kotak', 15000, 25000, 3);

-- --------------------------------------------------------

--
-- Table structure for table `dtltransbeli`
--

CREATE TABLE IF NOT EXISTS `dtltransbeli` (
  `noTrans` char(10) NOT NULL,
  `kdBrg` char(10) NOT NULL,
  `unit` int(4) NOT NULL,
  `jlhHarga` int(9) NOT NULL,
  PRIMARY KEY (`noTrans`,`kdBrg`),
  KEY `kdBrg` (`kdBrg`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dtltransbeli`
--

INSERT INTO `dtltransbeli` (`noTrans`, `kdBrg`, `unit`, `jlhHarga`) VALUES
('10001', 'hdh000001', 12, 200000);

-- --------------------------------------------------------

--
-- Table structure for table `dtltransjual`
--

CREATE TABLE IF NOT EXISTS `dtltransjual` (
  `noTrans` char(10) NOT NULL,
  `kdBrg` char(10) NOT NULL,
  `diskon` int(2) DEFAULT NULL,
  `hrgDiskon` int(8) NOT NULL,
  `jumlah` int(4) NOT NULL,
  `jlhHarga` int(9) NOT NULL,
  `laba` int(9) DEFAULT NULL,
  PRIMARY KEY (`noTrans`,`kdBrg`),
  KEY `kdBrg` (`kdBrg`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dtltransjual`
--

INSERT INTO `dtltransjual` (`noTrans`, `kdBrg`, `diskon`, `hrgDiskon`, `jumlah`, `jlhHarga`) VALUES
('jdh1500001', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500001', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500002', 'hdh000002', 0, 35000, 10, 350000),
('jdh1500002', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500002', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500003', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500003', 'hdh000005', 10, 40500, 1, 40500),
('jdh1500004', 'hdh000004', 0, 50000, 3, 150000),
('jdh1500005', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500006', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500006', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500007', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500007', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500007', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500007', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500008', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500008', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500008', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500008', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500009', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500009', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500009', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500009', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500009', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500010', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500010', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500010', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500010', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500011', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500012', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500013', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500014', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500014', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500015', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500016', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500016', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500017', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500018', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500019', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500019', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500020', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500020', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500021', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500021', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500021', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500021', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500021', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500021', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500021', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500022', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500022', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500023', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500024', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500025', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500026', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500026', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500026', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500027', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500028', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500028', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500028', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500028', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500028', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500028', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500028', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500029', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500030', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500030', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500030', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500030', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500030', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500030', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500030', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500031', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500031', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500031', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500031', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500031', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500032', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500032', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500032', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500032', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500032', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500033', 'hdh000002', 0, 35000, 5, 175000),
('jdh1500033', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500033', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500034', 'hdh000002', 0, 28000, 1, 28000),
('jdh1500034', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500034', 'hdh000007', 5, 23750, 1, 23750),
('jdh1500035', 'hdh000004', 0, 50000, 2, 100000),
('jdh1500036', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500036', 'hdh000004', 5, 47500, 3, 142500),
('jdh1500037', 'hdh000002', 0, 35000, 5, 175000),
('jdh1500038', 'hdh000001', 0, 28000, 4, 112000),
('jdh1500039', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500039', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500039', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500039', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500040', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500040', 'hdh000007', 0, 25000, 5, 125000),
('jdh1500041', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500041', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500042', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500042', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500042', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500042', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500043', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500043', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500043', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500044', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500045', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500046', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500047', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500048', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500049', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500050', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500051', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500051', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500052', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500052', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500052', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500053', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500053', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500054', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500055', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500055', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500056', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500056', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500056', 'hdh000007', 10, 22500, 1, 22500),
('jdh1500057', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500057', 'hdh000006', 0, 25000, 3, 75000),
('jdh1500058', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500059', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500059', 'hdh000003', 0, 45000, 1, 45000),
('jdh1500059', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500060', 'hdh000001', 0, 28000, 3, 84000),
('jdh1500060', 'hdh000002', 20, 28000, 1, 28000),
('jdh1500060', 'hdh000004', 0, 50000, 1, 50000),
('jdh1500060', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500061', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500061', 'hdh000007', 0, 25000, 4, 100000),
('jdh1500062', 'hdh000005', 0, 45000, 1, 45000),
('jdh1500062', 'hdh000007', 10, 22500, 1, 22500),
('jdh1500063', 'hdh000001', 0, 28000, 2, 56000),
('jdh1500063', 'hdh000004', 10, 45000, 1, 45000),
('jdh1500063', 'hdh000006', 0, 25000, 1, 25000),
('jdh1500064', 'hdh000002', 0, 35000, 1, 35000),
('jdh1500064', 'hdh000006', 0, 25000, 2, 50000),
('jdh1500064', 'hdh000007', 0, 25000, 1, 25000),
('jdh1500065', 'hdh000001', 0, 28000, 1, 28000),
('jdh1500065', 'hdh000005', 0, 45000, 1, 45000);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE IF NOT EXISTS `karyawan` (
  `idKar` char(5) NOT NULL,
  `nmKar` varchar(40) NOT NULL,
  `jabatan` varchar(8) NOT NULL,
  `pass` varchar(16) NOT NULL,
  PRIMARY KEY (`idKar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`idKar`, `nmKar`, `jabatan`, `pass`) VALUES
('dh001', 'Putra', 'Pemilik', '80694150'),
('dh002', 'Lisa', 'Pemilik', '654321'),
('dh003', 'Lesta', 'Karyawan', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE IF NOT EXISTS `pelanggan` (
  `idPel` char(10) NOT NULL,
  `nmPel` varchar(40) NOT NULL,
  `telpPel` varchar(13) NOT NULL,
  `almtPel` varchar(80) NOT NULL,
  PRIMARY KEY (`idPel`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`idPel`, `nmPel`, `telpPel`, `almtPel`) VALUES
('pdh00001', 'Anton', '081258796648', 'Jl. Mawar, Delitua'),
('pdh00002', 'Sri', '082275281961', 'Jl. Cempaka, Delitua'),
('pdh00003', 'Dewi', '082168265592', 'Jl. Roso, Marendal'),
('pdh00004', 'Hartono', '08127511685', 'Jl. Stasiun, Delitua'),
('pdh00005', 'Dewi', '082168265592', 'Jl. Roso, Marendal'),
('pdh00006', 'Mulia', '0813658715648', 'Jl. Darussalam, Medan'),
('pdh00007', 'Ferdy', '082268766159', 'Jl Patumbak, Patumbak'),
('pdh00008', 'Koko Satria', '0811697223', 'Jl Karya Wisata, Medan Johor'),
('pdh00009', 'Ferdy', '081579522165', 'Jl. STM Ujung, Medan');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `idSup` char(6) NOT NULL,
  `nmSup` varchar(30) NOT NULL,
  `salesman` varchar(40) NOT NULL,
  `telpSup` varchar(13) NOT NULL,
  `almtSup` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`idSup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`idSup`, `nmSup`, `salesman`, `telpSup`, `almtSup`) VALUES
('sdh001', 'Harokah', 'Andi', '082257918465', 'Jl. Denai, Medan'),
('sdh002', 'BLHerbal', 'Zulfikar', '081375246382', 'Marendal'),
('sdh003', 'Nyonya Meneer', 'Rizal', '081375461624', 'Jl. Gatot Subroto, Medan'),
('sdh004', 'Borobudur', 'Hendra', '081275442564', 'Medan');

-- --------------------------------------------------------

--
-- Table structure for table `transbeli`
--

CREATE TABLE IF NOT EXISTS `transbeli` (
  `noTrans` char(10) NOT NULL,
  `tglTrans` date NOT NULL,
  `idKar` char(5) NOT NULL,
  `idSup` char(6) NOT NULL,
  `subTtl` int(8) NOT NULL,
  `disc` float NOT NULL,
  `ttlHarga` int(9) NOT NULL,
  PRIMARY KEY (`noTrans`),
  KEY `idKar` (`idKar`),
  KEY `idSup` (`idSup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transbeli`
--

INSERT INTO `transbeli` (`noTrans`, `tglTrans`, `idKar`, `idSup`, `subTtl`, `disc`, `ttlHarga`) VALUES
('10001', '2015-10-22', 'dh001', 'sdh001', 50000, 0, 50000);

-- --------------------------------------------------------

--
-- Table structure for table `transjual`
--

CREATE TABLE IF NOT EXISTS `transjual` (
  `noTrans` char(10) NOT NULL,
  `tglTrans` date NOT NULL,
  `idPel` char(10) NOT NULL,
  `idKar` char(5) NOT NULL,
  `subTtl` int(8) NOT NULL,
  `disc` int(2) NOT NULL,
  `ttlHarga` int(9) NOT NULL,
  PRIMARY KEY (`noTrans`),
  KEY `idPel` (`idPel`),
  KEY `idKar` (`idKar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transjual`
--

INSERT INTO `transjual` (`noTrans`, `tglTrans`, `idPel`, `idKar`, `subTtl`, `disc`, `ttlHarga`) VALUES
('jdh1500001', '2015-10-14', 'pdh00004', 'dh001', 75000, 0, 75000),
('jdh1500002', '2015-10-14', 'pdh00005', 'dh001', 425000, 0, 425000),
('jdh1500003', '2015-10-14', 'pdh00004', 'dh001', 68500, 0, 68500),
('jdh1500004', '2015-10-14', 'pdh00007', 'dh001', 150000, 0, 150000),
('jdh1500005', '2015-10-14', 'pdh00001', 'dh001', 25000, 0, 25000),
('jdh1500006', '2015-10-14', 'pdh00007', 'dh001', 75000, 0, 75000),
('jdh1500007', '2015-10-14', 'pdh00002', 'dh001', 130000, 0, 130000),
('jdh1500008', '2015-10-14', 'pdh00005', 'dh001', 138000, 0, 138000),
('jdh1500009', '2015-10-14', 'pdh00002', 'dh001', 158000, 0, 158000),
('jdh1500010', '2015-10-14', 'pdh00002', 'dh001', 138000, 0, 138000),
('jdh1500011', '2015-10-14', 'pdh00001', 'dh001', 50000, 0, 50000),
('jdh1500012', '2015-10-14', 'pdh00001', 'dh001', 0, 0, 0),
('jdh1500013', '2015-10-14', 'pdh00005', 'dh001', 45000, 0, 45000),
('jdh1500014', '2015-10-14', 'pdh00006', 'dh001', 70000, 0, 70000),
('jdh1500015', '2015-10-14', 'pdh00001', 'dh001', 35000, 0, 35000),
('jdh1500016', '2015-10-14', 'pdh00006', 'dh001', 95000, 0, 95000),
('jdh1500017', '2015-10-14', 'pdh00001', 'dh001', 28000, 0, 28000),
('jdh1500018', '2015-10-14', 'pdh00001', 'dh001', 35000, 0, 35000),
('jdh1500019', '2015-10-14', 'pdh00001', 'dh001', 0, 0, 0),
('jdh1500020', '2015-10-14', 'pdh00005', 'dh001', 60000, 0, 60000),
('jdh1500021', '2015-10-14', 'pdh00001', 'dh001', 253000, 0, 253000),
('jdh1500022', '2015-10-14', 'pdh00005', 'dh001', 70000, 0, 70000),
('jdh1500023', '2015-10-14', 'pdh00004', 'dh001', 25000, 0, 25000),
('jdh1500024', '2015-10-14', 'pdh00002', 'dh001', 35000, 0, 35000),
('jdh1500025', '2015-10-14', 'pdh00007', 'dh001', 45000, 0, 45000),
('jdh1500026', '2015-10-14', 'pdh00008', 'dh001', 105000, 0, 105000),
('jdh1500027', '2015-10-14', 'pdh00005', 'dh001', 35000, 0, 35000),
('jdh1500028', '2015-10-15', 'pdh00001', 'dh001', 253000, 0, 253000),
('jdh1500029', '2015-10-15', 'pdh00006', 'dh001', 25000, 0, 25000),
('jdh1500030', '2015-10-15', 'pdh00003', 'dh003', 1450000, 0, 145000),
('jdh1500031', '2015-10-15', 'pdh00006', 'dh001', 100000, 0, 100000),
('jdh1500032', '2015-10-15', 'pdh00004', 'dh001', 183000, 0, 183000),
('jdh1500033', '2015-10-15', 'pdh00002', 'dh001', 245000, 0, 245000),
('jdh1500034', '2015-10-15', 'pdh00004', 'dh001', 96750, 0, 96750),
('jdh1500035', '2015-10-15', 'pdh00005', 'dh001', 100000, 0, 100000),
('jdh1500036', '2015-10-15', 'pdh00002', 'dh001', 170500, 0, 170500),
('jdh1500037', '2015-10-15', 'pdh00005', 'dh001', 175000, 0, 175000),
('jdh1500038', '2015-10-15', 'pdh00005', 'dh001', 112000, 0, 112000),
('jdh1500039', '2015-10-15', 'pdh00001', 'dh001', 155000, 0, 155000),
('jdh1500040', '2015-10-15', 'pdh00002', 'dh001', 375000, 0, 375000),
('jdh1500041', '2015-10-15', 'pdh00005', 'dh001', 70000, 0, 70000),
('jdh1500042', '2015-10-15', 'pdh00005', 'dh001', 148000, 0, 148000),
('jdh1500043', '2015-10-15', 'pdh00005', 'dh001', 120000, 0, 120000),
('jdh1500044', '2015-10-15', 'pdh00001', 'dh001', 35000, 0, 35000),
('jdh1500045', '2015-10-15', 'pdh00002', 'dh001', 25000, 0, 25000),
('jdh1500046', '2015-10-15', 'pdh00001', 'dh001', 35000, 0, 35000),
('jdh1500047', '2015-10-15', 'pdh00004', 'dh001', 45000, 0, 45000),
('jdh1500048', '2015-10-15', 'pdh00001', 'dh001', 35000, 0, 35000),
('jdh1500049', '2015-10-15', 'pdh00005', 'dh001', 50000, 0, 50000),
('jdh1500050', '2015-10-15', 'pdh00004', 'dh001', 25000, 0, 25000),
('jdh1500051', '2015-10-15', 'pdh00001', 'dh001', 75000, 0, 75000),
('jdh1500052', '2015-10-15', 'pdh00002', 'dh001', 100000, 0, 100000),
('jdh1500053', '2015-10-15', 'pdh00006', 'dh001', 80000, 0, 80000),
('jdh1500054', '2015-10-15', 'pdh00005', 'dh001', 45000, 0, 45000),
('jdh1500055', '2015-10-15', 'pdh00004', 'dh001', 320000, 0, 320000),
('jdh1500056', '2015-10-15', 'pdh00006', 'dh001', 107500, 0, 107500),
('jdh1500057', '2015-10-15', 'pdh00006', 'dh001', 120000, 0, 120000),
('jdh1500058', '2015-10-15', 'pdh00004', 'dh001', 25000, 0, 25000),
('jdh1500059', '2015-10-15', 'pdh00005', 'dh001', 150000, 0, 150000),
('jdh1500060', '2015-10-16', 'pdh00005', 'dh001', 187000, 0, 187000),
('jdh1500061', '2015-10-16', 'pdh00004', 'dh001', 125000, 0, 125000),
('jdh1500062', '2015-10-16', 'pdh00005', 'dh001', 67500, 0, 67500),
('jdh1500063', '2015-10-16', 'pdh00006', 'dh001', 126000, 10, 113400),
('jdh1500064', '2015-10-16', 'pdh00005', 'dh001', 110000, 15, 93500),
('jdh1500065', '2015-10-16', 'pdh00002', 'dh001', 73000, 20, 58400);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dtltransbeli`
--
ALTER TABLE `dtltransbeli`
  ADD CONSTRAINT `dtlTransBeli_ibfk_1` FOREIGN KEY (`kdBrg`) REFERENCES `barang` (`kdBrg`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `dtltransjual`
--
ALTER TABLE `dtltransjual`
  ADD CONSTRAINT `dtlTransJual_ibfk_1` FOREIGN KEY (`kdBrg`) REFERENCES `barang` (`kdBrg`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transbeli`
--
ALTER TABLE `transbeli`
  ADD CONSTRAINT `transBeli_ibfk_1` FOREIGN KEY (`noTrans`) REFERENCES `dtltransbeli` (`noTrans`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transBeli_ibfk_2` FOREIGN KEY (`idKar`) REFERENCES `karyawan` (`idKar`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transBeli_ibfk_3` FOREIGN KEY (`idSup`) REFERENCES `supplier` (`idSup`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transjual`
--
ALTER TABLE `transjual`
  ADD CONSTRAINT `transJual_ibfk_1` FOREIGN KEY (`noTrans`) REFERENCES `dtltransjual` (`noTrans`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transJual_ibfk_2` FOREIGN KEY (`idPel`) REFERENCES `pelanggan` (`idPel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transJual_ibfk_3` FOREIGN KEY (`idKar`) REFERENCES `karyawan` (`idKar`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
