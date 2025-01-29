"use client"

import dayjs from 'dayjs';

export default function CurrentDate({date}: {date: string}) {

  console.log(date)

  const now = dayjs(date).format('YYYY-MM-DD');

  return <span>{now}</span>
}