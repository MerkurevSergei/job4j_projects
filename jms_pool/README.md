[![Build Status](https://travis-ci.org/MerkurevSergei/job4j_JMSPool.svg?branch=master)](https://travis-ci.org/MerkurevSergei/job4j_JMSPool)
[![codecov](https://codecov.io/gh/MerkurevSergei/job4j_JMSPool/branch/master/graph/badge.svg)](https://codecov.io/gh/MerkurevSergei/job4j_JMSPool)

# JMS Pool
The RabbitMQ analog to asynchronous queue.
The app starts and waits client connections.
There are two types of clients: senders (publisher) and receiver (subscriber).
The protocol uses a simple HTTP implementation, messaging
performed in JSON format.
Implemented two modes message exchange: queue, topic.

#### Queue
The sender sends a message to specified queue.
The recipient reads the first message and deletes it from the queue.
Multiple recipients read a unique message from the same queue can only 
be read by one recipient.

#### Topic
The sender sends a message with the subject.
The recipient reads the first message and deletes it from the queue.
Each reader receives a copy of the message queue at the time
your first access to the service.

# JMS Pool
Аналог асинхронной очереди RabbitMQ. 
Приложение запускает Socket и ждет клиентов. 
Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber). 
В качестве протокола используется упрощенная реализация HTTP, обмен сообщениями 
осуществляется в формате JSON. 
Реализовано два режима обмена сообщениями: queue, topic.

#### Queue
Отправитель посылает сообщение с указанием очереди.
Получатель читает первое сообщение и удаляет его из очереди. 
Несколько получателей читают из одной очереди, уникальное сообщение 
может быть прочитано, только одним получателем. 

#### Topic
Отправитель посылает сообщение с указанием темы.
Получатель читает первое сообщение и удаляет его из очереди. 
Каждый читатель получает копию очереди сообщений на момент 
своего первого обращения к сервису.
