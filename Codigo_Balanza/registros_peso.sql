USE [Pruebas]
GO

/****** Object:  Table [dbo].[registros_peso]    Script Date: 11/3/2025 14:30:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[registros_peso](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[peso] [decimal](10, 3) NULL,
	[fecha] [date] NULL,
	[hora] [time](0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[registros_peso] ADD  DEFAULT (getdate()) FOR [fecha]
GO

ALTER TABLE [dbo].[registros_peso] ADD  DEFAULT (CONVERT([time],getdate())) FOR [hora]
GO


