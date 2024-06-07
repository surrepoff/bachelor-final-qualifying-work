import configparser

from sqlalchemy.ext.asyncio import create_async_engine, async_sessionmaker


config = configparser.ConfigParser()
config.read('config.ini')

DATABASE_URL = ("postgresql+asyncpg://" + config['DATABASE']['username'] + ":" + config['DATABASE']['password'] + "@" +
                config['DATABASE']['ip'] + ":" + config['DATABASE']['port'] + "/" + config['DATABASE']['database_name'])

engine = create_async_engine(DATABASE_URL)

new_session = async_sessionmaker(engine, expire_on_commit=False)
